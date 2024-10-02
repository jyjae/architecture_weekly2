package io.hhplus.architecture.application.enrollment;

import io.hhplus.architecture.application.lecture.LectureService;
import io.hhplus.architecture.application.user.UserService;
import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EnrollmentFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentFacadeService.class);

    private final EnrollmentService enrollmentService;
    private final UserService userService;
    private final LectureService lectureService;

    public EnrollmentFacadeService(
            EnrollmentService enrollmentService,
            UserService userService,
            LectureService lectureService
    ) {
        this.enrollmentService = enrollmentService;
        this.userService = userService;
        this.lectureService = lectureService;
    }

    /**
     * 사용자를 강의에 수강신청 처리하는 메서드.
     *
     * @param command 수강신청에 필요한 userId와 lectureId를 포함하는 명령 객체.
     * @return 수강신청 성공 시 반환되는 Enrollment 객체.
     */
    @Transactional
    public Enrollment enrollment(RegisterEnrollmentCommand command) {

        // lectureId로 강의 조회
        Lecture lecture = lectureService.findByIdWithLock(command.lectureId());

        // userId로 사용자 조회
        User user = userService.findById(command.userId());

        // 수강신청 처리
        Enrollment enrollment = enrollmentService.enrollment(user, lecture);

        lectureService.increaseEnrollmentCount(lecture);
        logger.info("Enrollment count increased for lectureId: {}. Current count: {}", lecture.getId(), lecture.getEnrollmentCount());

        return enrollment;
    }


}
