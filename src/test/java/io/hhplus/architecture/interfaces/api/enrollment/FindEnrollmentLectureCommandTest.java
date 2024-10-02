package io.hhplus.architecture.interfaces.api.enrollment;

import io.hhplus.architecture.exception.InvalidRequestException;
import io.hhplus.architecture.application.lecture.FindEnrollmentLectureCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindEnrollmentLectureCommandTest {

    @DisplayName("command 생성 성공")
    @Test
    void shouldCreateCommandWhenValidUserId() {
        // Given

        // When
        FindEnrollmentLectureCommand command = new FindEnrollmentLectureCommand(1L);

        // Then
        assertThat(command.userId()).isEqualTo(1L);
    }

    @DisplayName("userId가 null일때 command 생성 실패")
    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        // Given

        // When / Then
        assertThatThrownBy(() -> new FindEnrollmentLectureCommand(null))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("userId는 null이거나 0 이하일 수 없습니다.");
    }


}