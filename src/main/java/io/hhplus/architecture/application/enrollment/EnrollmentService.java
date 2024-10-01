package io.hhplus.architecture.application.enrollment;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.enrollment.EnrollmentRepository;
import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.user.User;
import io.hhplus.architecture.exception.InvalidRequestException;
import io.hhplus.architecture.exception.ResourceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * 수강신청 등록 메서드
     * @param user, lecture - user 도메인 객체, lecture 도메인 객체
     * @return - 수강신청 도메인 entity (Enrollment)
     */
    public Enrollment enrollment(User user, Lecture lecture) {
        // 수강신청 이미 했는지 처리 로직
        if (isEnrolled(user.getId(), lecture.getId())) {
            throw new ResourceAlreadyExistsException("이미 수강신청 했습니다.");
        }

        // 수용인원 찼는지 처리 로직
        if(!lecture.isAvailableForEnrollment()) {
            throw new InvalidRequestException("수강 인원이 꽉찼습니다.");
        }


        // Enrollment 값 변환 및 저장 처리
        Enrollment enrollment = enrollmentRepository.enrollment(user, lecture);
        logger.info("Enrollment created for userId: {} and lectureId: {}", user.getId(), lecture.getId());

        return enrollment;
    }

    /**
     * 수강신청 여부 조회 메서드
     * @param userId, lectureId - 유저 ID와 강의 ID
     * @return - 수강신청 여부 (boolean)
     */
    @Transactional(readOnly = true)
    public boolean isEnrolled(Long userId, Long lectureId) {
        return enrollmentRepository.isEnrolled(userId, lectureId);
    }

    /**
     * 사용자가 수강신청한 강의 목록 조회 메서드
     * @param userId - 유저 ID
     * @return - 수강신청 목록 (List<Enrollment>)
     */
    @Transactional(readOnly = true)
    public List<Enrollment> findUserEnrolledLectures(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        logger.info("Found {} enrollments for userId: {}", enrollments.size(), userId);
        return enrollments;
    }
}
