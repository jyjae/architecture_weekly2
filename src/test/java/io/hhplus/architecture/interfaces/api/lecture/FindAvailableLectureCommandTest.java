package io.hhplus.architecture.interfaces.api.lecture;

import io.hhplus.architecture.application.lecture.FindAvailableLectureCommand;
import io.hhplus.architecture.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindAvailableLectureCommandTest {

    @DisplayName("command 생성 성공")
    @Test
    void shouldCreateCommandWhenValidUserIdAndStartDate() {
        // Given
        Long userId = 1L;
        LocalDateTime validStartDate = LocalDateTime.now().plusDays(1); // 미래 날짜

        // When
        FindAvailableLectureCommand command = new FindAvailableLectureCommand(userId, validStartDate);

        // Then
        assertThat(command.userId()).isEqualTo(userId);
        assertThat(command.startDate()).isEqualTo(validStartDate);
    }

    @DisplayName("userId가 null일때 command 생성 실패")
    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        // Given
        LocalDateTime validStartDate = LocalDateTime.now().plusDays(1);

        // When / Then
        assertThatThrownBy(() -> new FindAvailableLectureCommand(null, validStartDate))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("userId는 null이거나 0 이하일 수 없습니다.");
    }

    @DisplayName("startDate가 null일때 command 생성 실패")
    @Test
    void shouldThrowExceptionWhenStartDateIsNull() {
        // Given
        Long userId = 1L;

        // When / Then
        assertThatThrownBy(() -> new FindAvailableLectureCommand(userId, null))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("startDate는 null일 수 없습니다.");
    }
}
