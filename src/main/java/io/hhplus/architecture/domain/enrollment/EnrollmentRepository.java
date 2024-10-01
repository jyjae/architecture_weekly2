package io.hhplus.architecture.domain.enrollment;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.user.User;

import java.util.List;

public interface EnrollmentRepository {

    public boolean isEnrolled(Long userId, Long lectureId);

    Enrollment enrollment(User user, Lecture lecture);

    List<Enrollment> findByUserId(Long userId);
}
