package io.hhplus.architecture.infrastructure.persistence.enrollment;

import io.hhplus.architecture.domain.enrollment.EnrollmentRepository;
import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.user.User;
import io.hhplus.architecture.infrastructure.persistence.lecture.LectureMapper;
import io.hhplus.architecture.infrastructure.persistence.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final EnrollmentJpaRepository enrollmentJpaRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final UserMapper userMapper;
    private final LectureMapper lectureMapper;

    @Autowired
    public EnrollmentRepositoryImpl(
            EnrollmentJpaRepository enrollmentJpaRepository,
            EnrollmentMapper enrollmentMapper,
            UserMapper userMapper, LectureMapper lectureMapper) {
        this.enrollmentJpaRepository = enrollmentJpaRepository;
        this.enrollmentMapper = enrollmentMapper;
        this.userMapper = userMapper;
        this.lectureMapper = lectureMapper;
    }

    /**
     * 사용자가 해당 강의를 수강 신청했는지 여부를 확인하는 메서드.
     *
     * @param userId 유저 ID
     * @param lectureId 강의 ID
     * @return 수강 신청 여부 (true/false)
     */
    @Override
    public boolean isEnrolled(Long userId, Long lectureId) {
        return enrollmentJpaRepository.existsByUserIdAndLectureId(userId, lectureId);
    }

    /**
     * 사용자와 강의를 기반으로 수강 신청을 처리하는 메서드.
     *
     * @param user 유저 도메인 객체
     * @param lecture 강의 도메인 객체
     * @return 도메인 객체로 변환된 Enrollment 객체
     */
    @Override
    public Enrollment enrollment(User user, Lecture lecture) {
        // User와 Lecture 객체를 JPA 엔티티로 변환한 후 수강 신청 엔티티로 변환
        EnrollmentJpaEntity entity = EnrollmentJpaEntity.toEntity(userMapper.mapToJpaEntity(user), lectureMapper.mapToJpaEntity(lecture));

        enrollmentJpaRepository.save(entity);

        // JPA 엔티티를 도메인 객체로 변환하여 반환
        return enrollmentMapper.mapToDomainEntity(entity);
    }

    /**
     * 특정 사용자가 수강 신청한 강의 목록을 조회하는 메서드.
     *
     * @param userId 유저 ID
     * @return 사용자가 수강 신청한 강의 목록 (Enrollment 리스트)
     */
    @Override
    public List<Enrollment> findByUserId(Long userId) {
        // userId로 수강 신청한 모든 엔티티를 조회한 후 도메인 객체로 변환하여 반환
        return enrollmentJpaRepository.findByUserId(userId)
                .stream()
                .map(entity -> enrollmentMapper.mapToDomainEntity(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> findByLectureId(Long lectureId) {
        return enrollmentJpaRepository.findByLectureId(lectureId)
                .stream()
                .map(entity -> enrollmentMapper.mapToDomainEntity(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Enrollment findByUserIdAndLectureId(Long userId, Long lectureId) {
        return enrollmentMapper.mapToDomainEntity(enrollmentJpaRepository.findByUserIdAndLectureId(userId, lectureId));
    }
}
