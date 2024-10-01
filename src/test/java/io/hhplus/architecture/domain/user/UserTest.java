package io.hhplus.architecture.domain.user;

import io.hhplus.architecture.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("User 생성 성공")
    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        User.UserId userId = new User.UserId(1L);
        User.UserName userName = new User.UserName("홍길동");

        // When
        User user = User.generateUser(userId, userName);

        // Then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("홍길동");
    }

    @DisplayName("User 생성 실패 - ID가 유효하지 않음")
    @Test
    void shouldFailWhenUserIdIsInvalid() {
        assertThatThrownBy(() -> new User.UserId(null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("userId는 null이거나 0 이하일 수 없습니다.");

        assertThatThrownBy(() -> new User.UserId(0L))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("userId는 null이거나 0 이하일 수 없습니다.");
    }

    @DisplayName("User 생성 실패 - 이름이 null임")
    @Test
    void shouldFailWhenUserNameIsInvalid() {
        assertThatThrownBy(() -> new User.UserName(null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("userName은 null일 수 없습니다.");
    }
}
