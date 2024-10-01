package io.hhplus.architecture.application.user;

import io.hhplus.architecture.domain.user.User;
import io.hhplus.architecture.domain.user.UserRepository;
import io.hhplus.architecture.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 ID로 유저를 조회하는 메서드.
     *
     * @param userId 유저 ID
     * @return User 도메인 객체
     * @throws NotFoundException 유저가 존재하지 않을 경우 예외 발생
     */
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않은 유저입니다."));
    }
}
