package io.hhplus.architecture.infrastructure.persistence.user;

import io.hhplus.architecture.domain.user.UserRepository;
import io.hhplus.architecture.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(
            UserJpaRepository userJpaRepository,
            UserMapper userMapper
    ) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    /**
     * 유저 ID로 유저를 조회하는 메서드.
     *
     * @param userId 조회할 유저의 ID
     * @return Optional로 감싼 User 도메인 객체. 유저가 없을 경우 빈 Optional 반환.
     */
    @Override
    public Optional<User> findById(Long userId) {
        // JPA 엔티티를 도메인 엔티티로 변환하여 반환
        return userMapper.mapToDomainEntityOptional(userJpaRepository.findById(userId));
    }

    // test 용
    @Override
    public void save(UserJpaEntity user) {
        userJpaRepository.save(user);
    }
}
