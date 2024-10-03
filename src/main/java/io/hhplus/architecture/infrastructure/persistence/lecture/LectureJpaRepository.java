package io.hhplus.architecture.infrastructure.persistence.lecture;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<LectureJpaEntity, Long> {
    @Query("SELECT l FROM LectureJpaEntity l WHERE (:lectureIds IS NULL OR l.id NOT IN :lectureIds) AND l.startTime = :startTime")
    List<LectureJpaEntity> findByIdNotInAndStartTimeEquals(@Param("lectureIds") List<Long> lectureIds, @Param("startTime") LocalDateTime startTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureJpaEntity> findById(Long lectureId);

    LectureJpaEntity findOneById(Long lectureId);
}
