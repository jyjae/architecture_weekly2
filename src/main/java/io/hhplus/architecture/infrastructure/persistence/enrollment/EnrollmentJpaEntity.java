package io.hhplus.architecture.infrastructure.persistence.enrollment;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.user.User;
import io.hhplus.architecture.infrastructure.persistence.lecture.LectureJpaEntity;
import io.hhplus.architecture.infrastructure.persistence.user.UserJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EnrollmentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;

    private Long userId;

    @CreationTimestamp
    private LocalDateTime enrollmentDate;

    @Builder
    public EnrollmentJpaEntity(
            Long userId,
            Long lectureId
    ) {
        this.userId = userId;
        this.lectureId = lectureId;
    }

    public static EnrollmentJpaEntity toEntity(
            UserJpaEntity user,
            LectureJpaEntity lecture
    ) {
        return EnrollmentJpaEntity.builder()
                .userId(user.getId())
                .lectureId(lecture.getId())
                .build();
    }
}
