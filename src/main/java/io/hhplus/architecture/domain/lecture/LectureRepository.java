package io.hhplus.architecture.domain.lecture;


import io.hhplus.architecture.infrastructure.persistence.lecture.LectureJpaEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

public interface LectureRepository {

    Optional<Lecture> findById(Long lectureId);

    List<Lecture> findAvailableLecturesByInIds(List<Long> lectureIds, LocalDateTime startDate);

    void save(LectureJpaEntity lecture);

    void save(Lecture lecture);
}
