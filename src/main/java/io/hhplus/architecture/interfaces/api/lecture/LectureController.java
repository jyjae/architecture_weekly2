package io.hhplus.architecture.interfaces.api.lecture;

import io.hhplus.architecture.application.lecture.FindAvailableLectureCommand;
import io.hhplus.architecture.application.lecture.FindEnrollmentLectureCommand;
import io.hhplus.architecture.application.lecture.LectureFacadeService;
import io.hhplus.architecture.domain.lecture.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/lecture")
@RestController
public class LectureController {

    private static final Logger log = LoggerFactory.getLogger(LectureController.class);
    private final LectureFacadeService lectureFacadeService;


    public LectureController(LectureFacadeService lectureFacadeService) {
        this.lectureFacadeService = lectureFacadeService;
    }


    /**
     * 수강신청 가능한 특강 목록 조회 API
     * @param userId - 유저 ID
     * @return - 특강 ResponseEntity
     */
    @GetMapping("/{userId}/{startTime}")
    public ResponseEntity<List<Lecture>> lecture(
            @PathVariable Long userId,
            @PathVariable LocalDateTime startTime
            ) {
        log.info("Enrolled lecture with UserID: {}, StartTime: {}", userId, startTime);
        return ResponseEntity.ok(lectureFacadeService.findAvailableLecturesForUser(new FindAvailableLectureCommand(userId, startTime)));
    }

    /**
     * 유저가 수강신청한 강의 목록 조회 API
     * @param userId - 유저 ID
     * @return - 수강신청 ResponseEntity
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Lecture>> enrollment(
            @PathVariable Long userId
    ) {
        log.info("Enrolled lecture with UserID: {}, LectureID: {}", userId);
        return ResponseEntity.ok(lectureFacadeService.enrollmentsForUser(new FindEnrollmentLectureCommand(userId)));
    }

}
