package io.hhplus.architecture.domain.lecture;
import io.hhplus.architecture.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Lecture {
    private Long id;
    private String title;
    private String description;
    private String instructor;
    private Long enrollmentCount;
    private LocalDateTime startTime;

    public static Lecture generateLecture(
            LectureId id,
            Title title,
            Description description,
            Instructor instructor,
            EnrollmentCount enrollmentCount,
            StartTime startTime) {
        return new Lecture(
                id.getLectureId(),
                title.getTitle(),
                description.getDescription(),
                instructor.getInstructor(),
                enrollmentCount.getEnrollmentCount(),
                startTime.getStartTime()
        );
    }

    public void increaseEnrollmentCount() {
        this.enrollmentCount++;
    }

    // LectureId 클래스
    @Value
    public static class LectureId {
        Long lectureId;

        public LectureId(Long value) {
            if (value == null || value <= 0) {
                throw new DomainValidationException("강의 ID는 0보다 커야 합니다.");
            }
            this.lectureId = value;
        }
    }

    // Title 클래스
    @Value
    public static class Title {
        String title;

        public Title(String value) {
            if (value == null || value.isBlank()) {
                throw new DomainValidationException("강의 제목은 비어있을 수 없습니다.");
            }
            this.title = value;
        }
    }

    // Description 클래스
    @Value
    public static class Description {
        String description;

        public Description(String value) {
            if (value == null || value.isBlank()) {
                throw new DomainValidationException("강의 설명은 비어있을 수 없습니다.");
            }
            this.description = value;
        }
    }

    // Instructor 클래스
    @Value
    public static class Instructor {
        String instructor;

        public Instructor(String value) {
            if (value == null || value.isBlank()) {
                throw new DomainValidationException("강사 이름은 비어있을 수 없습니다.");
            }
            this.instructor = value;
        }
    }

    // EnrollmentCount 클래스
    @Value
    public static class EnrollmentCount {
        Long enrollmentCount;

        public EnrollmentCount(Long value) {
            if (value == null || value < 0) {
                throw new DomainValidationException("수강 인원은 0 이상이어야 합니다.");
            }
            this.enrollmentCount = value;
        }
    }

    // StartTime 클래스
    @Value
    public static class StartTime {
        LocalDateTime startTime;

        public StartTime(LocalDateTime value) {
            if (value == null) {
                throw new DomainValidationException("강의 시작 시간은 비어있을 수 없습니다.");
            }
            this.startTime = value;
        }
    }

    // 수강 인원이 30명 이하인지 체크하는 도메인 규칙
    public boolean isAvailableForEnrollment() {
        return this.enrollmentCount < 30L;
    }
}
