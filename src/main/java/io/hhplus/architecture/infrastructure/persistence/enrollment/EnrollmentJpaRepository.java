package io.hhplus.architecture.infrastructure.persistence.enrollment;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByUserIdAndLectureId(Long userId, Long lectureId);

    List<EnrollmentJpaEntity> findByUserId(Long userId);

    List<EnrollmentJpaEntity> findByLectureId(long lectureId);

    EnrollmentJpaEntity findByUserIdAndLectureId(Long userId, Long lectureId);
}
