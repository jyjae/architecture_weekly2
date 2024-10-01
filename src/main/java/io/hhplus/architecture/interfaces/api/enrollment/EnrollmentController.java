package io.hhplus.architecture.interfaces.api.enrollment;

import io.hhplus.architecture.application.enrollment.EnrollmentFacadeService;
import io.hhplus.architecture.domain.enrollment.Enrollment;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequestMapping("/enrollment")
@RestController
public class EnrollmentController {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);
    private final EnrollmentFacadeService enrollmentFacadeService;

    public EnrollmentController(EnrollmentFacadeService enrollmentFacadeService) {
        this.enrollmentFacadeService = enrollmentFacadeService;
    }


    /**
     * 수강신청하는 API
     * @param userId - 유저 ID
     * @param lectureId - 강의 ID
     * @return - 수강신청 ResponseEntity
     */
    @PostMapping("/{userId}/{lectureId}")
    public ResponseEntity<Enrollment> enrollment(
            @PathVariable Long userId,
            @PathVariable Long lectureId
    ) {
        log.info("Enrollment lecture with UserID: {}, LectureID: {}", userId, lectureId);
        return ResponseEntity.ok(enrollmentFacadeService.enrollment(new RegisterEnrollmentCommand(userId, lectureId)));
    }

    /**
     * 유저가 수강신청한 강의 목록 조회 API
     * @param userId - 유저 ID
     * @return - 수강신청 ResponseEntity
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Enrollment>> enrollment(
            @PathVariable Long userId
    ) {
        log.info("Enrolled lecture with UserID: {}, LectureID: {}", userId);
        return ResponseEntity.ok(enrollmentFacadeService.enrollmentsForUser(new FindEnrollmentLectureCommand(userId)));
    }

}
