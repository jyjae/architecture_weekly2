package io.hhplus.architecture.infrastructure.persistence.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LectureMapper {

    public Optional<Lecture> mapToDomainEntityOptional(Optional<LectureJpaEntity> optionalLectureJpaEntity) {
        return optionalLectureJpaEntity.map(lectureJpaEntity -> Lecture.generateLecture(
                new Lecture.LectureId(lectureJpaEntity.getId()),
                new Lecture.Title(lectureJpaEntity.getTitle()),
                new Lecture.Description(lectureJpaEntity.getDescription()),
                new Lecture.Instructor(lectureJpaEntity.getInstructor()),
                new Lecture.EnrollmentCount(lectureJpaEntity.getEnrollmentCount()),
                new Lecture.StartTime(lectureJpaEntity.getStartTime())
        ));
    }

    public LectureJpaEntity mapToJpaEntity(Lecture lecture) {
        // 도메인 UserId를 UserJpaEntity로 변환
        return LectureJpaEntity.toEntity(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getDescription(),
                lecture.getInstructor(),
                lecture.getEnrollmentCount(),
                lecture.getStartTime());  // 이름을 null로 설정했지만 필요에 따라 처리
    }

    public Lecture mapToDomainEntity(LectureJpaEntity lectureJpaEntity) {
        return Lecture.generateLecture(
                new Lecture.LectureId(lectureJpaEntity.getId()),
                new Lecture.Title(lectureJpaEntity.getTitle()),
                new Lecture.Description(lectureJpaEntity.getDescription()),
                new Lecture.Instructor(lectureJpaEntity.getInstructor()),
                new Lecture.EnrollmentCount(lectureJpaEntity.getEnrollmentCount()),
                new Lecture.StartTime(lectureJpaEntity.getStartTime())
        );
    }
}
