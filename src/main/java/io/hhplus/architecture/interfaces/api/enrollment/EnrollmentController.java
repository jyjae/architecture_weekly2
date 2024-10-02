package io.hhplus.architecture.interfaces.api.enrollment;

import io.hhplus.architecture.application.enrollment.EnrollmentFacadeService;
import io.hhplus.architecture.application.enrollment.RegisterEnrollmentCommand;
import io.hhplus.architecture.domain.enrollment.Enrollment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
