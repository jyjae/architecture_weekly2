package io.hhplus.architecture.domain.user;

import io.hhplus.architecture.domain.user.User;
import io.hhplus.architecture.infrastructure.persistence.user.UserJpaEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public Optional<User> findById(Long userId);

    void save(UserJpaEntity user2);
}
