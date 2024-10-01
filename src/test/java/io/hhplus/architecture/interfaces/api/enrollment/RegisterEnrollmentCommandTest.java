package io.hhplus.architecture.interfaces.api.enrollment;

import io.hhplus.architecture.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class RegisterEnrollmentCommandTest {

    @DisplayName("command 생성 성공")
    @Test
    void shouldCreateCommandWhenValidUserIdAndLectureId() {
        // Given

        // When
        RegisterEnrollmentCommand command = new RegisterEnrollmentCommand(1L, 1L);

        // Then
        assertThat(command.userId()).isEqualTo(1L);
        assertThat(command.lectureId()).isEqualTo(1L);
    }

    @DisplayName("userId가 null일때 command 생성 실패")
    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        // Given

        // When / Then
        assertThatThrownBy(() -> new RegisterEnrollmentCommand(null, 1L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("userId는 null이거나 0 이하일 수 없습니다.");
    }

    @DisplayName("userId가 0일때 command 생성 실패")
    @Test
    void shouldThrowExceptionWhenUserIdIsZeroOrLess() {
        // Given

        // When / Then
        assertThatThrownBy(() -> new RegisterEnrollmentCommand(0L, 1L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("userId는 null이거나 0 이하일 수 없습니다.");
    }

    @DisplayName("lectureId가 null일때 command 생성 실패")
    @Test
    void shouldThrowExceptionWhenLectureIdIsNull() {
        // Given

        // When / Then
        assertThatThrownBy(() -> new RegisterEnrollmentCommand(1L, null))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("lectureId는 null이거나 0 이하일 수 없습니다.");
    }

    @DisplayName("lectureId가 0일때 command 생성 실패")
    @Test
    void shouldThrowExceptionWhenLectureIdIsZeroOrLess() {
        // Given

        // When / Then
        assertThatThrownBy(() -> new RegisterEnrollmentCommand(1L, 0L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("lectureId는 null이거나 0 이하일 수 없습니다.");
    }
}
