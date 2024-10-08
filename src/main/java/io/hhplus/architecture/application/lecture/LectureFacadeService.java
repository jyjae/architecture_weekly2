package io.hhplus.architecture.application.lecture;

import io.hhplus.architecture.application.enrollment.EnrollmentService;
import io.hhplus.architecture.application.user.UserService;
import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.lecture.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LectureFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(LectureFacadeService.class);

    private final EnrollmentService enrollmentService;
    private final UserService userService;
    private final LectureService lectureService;

    public LectureFacadeService(
            EnrollmentService enrollmentService,
            UserService userService,
            LectureService lectureService
    ) {
        this.enrollmentService = enrollmentService;
        this.userService = userService;
        this.lectureService = lectureService;
    }

    /**
     * 사용자가 수강 신청할 수 있는 강의 목록을 조회하는 메서드.
     *
     * @param command 수강 신청 가능 강의 조회에 필요한 userId와 startDate를 포함한 명령 객체.
     * @return 사용자가 수강 신청할 수 있는 Lecture 목록.
     */
    public List<Lecture> findAvailableLecturesForUser(FindAvailableLectureCommand command) {
        // userId로 사용자가 수강신청한 강의 ID 목록을 가져옴
        List<Long> enrolledLectureIds = enrollmentService.findUserEnrolledLectures(command.userId())
                .stream()
                .map(Enrollment::getLectureId)
                .collect(Collectors.toList());

        // 해당 강의들에 대한 정보를 가져옴
        List<Lecture> availableLectures = lectureService.findAvailableLecturesExcluding(enrolledLectureIds, command.startDate());

        return availableLectures;
    }

    /**
     * 사용자의 수강신청 내역을 조회하는 메서드.
     *
     * @param command 수강신청 내역 조회에 필요한 userId를 포함하는 명령 객체.
     * @return 사용자가 수강신청한 Enrollment 목록.
     */
    public List<Lecture> enrollmentsForUser(FindEnrollmentLectureCommand command) {

        // userId로 사용자 존재 확인
        userService.findById(command.userId());

        // 사용자가 수강신청한 강의 목록 조회
        List<Enrollment> enrollments = enrollmentService.findUserEnrolledLectures(command.userId());

        return lectureService.findByIds(enrollments
                .stream().
                map(enrollment -> enrollment.getLectureId())
                .collect(Collectors.toList()));
    }

}
