package io.hhplus.architecture.domain.enrollment;

import io.hhplus.architecture.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Enrollment {
    private final Long userId;
    private final Long lectureId;
    private final String lectureTitle;
    private final String instructor;
    private final LocalDateTime enrollmentDate;

    public static Enrollment generateEnrollment(
            UserId userId,
            LectureId lectureId,
            LectureTitle lectureTitle,
            Instructor instructor,
            EnrollmentDate enrollmentDate) {
        return new Enrollment(
                userId.userId,
                lectureId.lectureId,
                lectureTitle.title,
                instructor.instructorName,
                enrollmentDate.enrollmentDate
        );
    }

    @Value
    public static class UserId {
        Long userId;

        public UserId(Long value) {
            if (value == null || value <= 0) {
                throw new DomainValidationException("유저 ID는 0보다 커야 합니다.");
            }
            this.userId = value;
        }
    }

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

    @Value
    public static class LectureTitle {
        String title;

        public LectureTitle(String value) {
            if (value == null || value.isBlank()) {
                throw new DomainValidationException("강의 제목은 비어있을 수 없습니다.");
            }
            this.title = value;
        }
    }

    @Value
    public static class Instructor {
        String instructorName;

        public Instructor(String value) {
            if (value == null || value.isBlank()) {
                throw new DomainValidationException("강사 이름은 비어있을 수 없습니다.");
            }
            this.instructorName = value;
        }
    }

    @Value
    public static class EnrollmentDate {
        LocalDateTime enrollmentDate;

        public EnrollmentDate(LocalDateTime value) {
            if (value == null) {
                throw new DomainValidationException("수강신청 날짜는 비어있을 수 없습니다.");
            }
            this.enrollmentDate = value;
        }
    }

}
