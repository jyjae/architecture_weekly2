package io.hhplus.architecture.application.user;

import io.hhplus.architecture.exception.NotFoundException;
import io.hhplus.architecture.infrastructure.persistence.user.UserRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepositoryImpl userRepository;

    @DisplayName("유저가 존재하지 않은 경우 예외 발생")
    @Test
    void shouldFailWhenUserNotFound() {
        // Given

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        // When

        // Then
        assertThatThrownBy(() -> userService.findById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않은 유저입니다.");
    }


}