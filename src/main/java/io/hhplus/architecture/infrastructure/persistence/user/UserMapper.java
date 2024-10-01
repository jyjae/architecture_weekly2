package io.hhplus.architecture.infrastructure.persistence.user;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.user.User;
import io.hhplus.architecture.infrastructure.persistence.enrollment.EnrollmentMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final EnrollmentMapper enrollmentMapper;

    public UserMapper(EnrollmentMapper enrollmentMapper) {
        this.enrollmentMapper = enrollmentMapper;
    }

    public Optional<User> mapToDomainEntityOptional(Optional<UserJpaEntity> optionalUserJpaEntity) {
        return optionalUserJpaEntity.map(userJpaEntity -> {
            return User.generateUser(
                    new User.UserId(userJpaEntity.getId()),
                    new User.UserName(userJpaEntity.getName()));
        });
    }

    public UserJpaEntity mapToJpaEntity(User user) {
        // 도메인 UserId를 UserJpaEntity로 변환
        return UserJpaEntity.toEntity(user.getId(), user.getName());  // 이름을 null로 설정했지만 필요에 따라 처리
    }
}
