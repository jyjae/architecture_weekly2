package io.hhplus.architecture.infrastructure.persistence.enrollment;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Long> {
    boolean existsByUserIdAndLectureId(Long userId, Long lectureId);

    List<EnrollmentJpaEntity> findByUserId(Long userId);
}
