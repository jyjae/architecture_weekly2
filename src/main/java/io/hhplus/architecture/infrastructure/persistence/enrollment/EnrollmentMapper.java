package io.hhplus.architecture.infrastructure.persistence.enrollment;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    /**
     * JPA 엔티티를 도메인 엔티티로 변환하는 메서드.
     *
     * @param entity JPA에서 사용되는 EnrollmentJpaEntity 객체
     * @return 도메인에서 사용하는 Enrollment 객체
     */
    public Enrollment mapToDomainEntity(EnrollmentJpaEntity entity) {
        return Enrollment.generateEnrollment(
                new Enrollment.UserId(entity.getUserId()),
                new Enrollment.LectureId(entity.getLectureId()),
                new Enrollment.LectureTitle(entity.getLectureTitle()),
                new Enrollment.Instructor(entity.getLectureInstructor()),
                new Enrollment.EnrollmentDate(entity.getEnrollmentDate())
        );
    }
}
