package io.hhplus.architecture.domain.enrollment;

import io.hhplus.architecture.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentTest {

    @DisplayName("Enrollment 생성 성공")
    @Test
    void shouldCreateEnrollmentSuccessfully() {
        // Given
        Enrollment.UserId userId = new Enrollment.UserId(1L);
        Enrollment.LectureId lectureId = new Enrollment.LectureId(1L);
        Enrollment.EnrollmentDate enrollmentDate = new Enrollment.EnrollmentDate(LocalDateTime.now());

        // When
        Enrollment enrollment = Enrollment.generateEnrollment(userId, lectureId, enrollmentDate);

        // Then
        assertThat(enrollment.getUserId()).isEqualTo(1L);
        assertThat(enrollment.getLectureId()).isEqualTo(1L);
    }

    @DisplayName("Enrollment 생성 실패 - 유저 ID가 유효하지 않음")
    @Test
    void shouldFailWhenUserIdIsInvalid() {
        assertThatThrownBy(() -> new Enrollment.UserId(null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("유저 ID는 0보다 커야 합니다.");
    }

    @DisplayName("Enrollment 생성 실패 - 수강신청 날짜가 null임")
    @Test
    void shouldFailWhenEnrollmentDateIsNull() {
        assertThatThrownBy(() -> new Enrollment.EnrollmentDate(null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("수강신청 날짜는 비어있을 수 없습니다.");
    }
}
