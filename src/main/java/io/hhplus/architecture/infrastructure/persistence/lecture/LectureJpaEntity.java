package io.hhplus.architecture.infrastructure.persistence.lecture;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LectureJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String instructor;

    private Long enrollmentCount;

    private LocalDateTime startTime;

    @Builder
    public LectureJpaEntity(Long id, String title, String description, String instructor, Long enrollmentCount, LocalDateTime startTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.enrollmentCount = enrollmentCount;
        this.startTime = startTime;
    }

    public static LectureJpaEntity toEntity(
            Long id,
            String title,
            String description,
            String instructor,
            Long enrollmentCount,
            LocalDateTime startTime
    ) {
        return LectureJpaEntity.builder()
                .id(id)
                .title(title)
                .description(description)
                .instructor(instructor)
                .enrollmentCount(enrollmentCount)
                .startTime(startTime)
                .build();
    }
}
