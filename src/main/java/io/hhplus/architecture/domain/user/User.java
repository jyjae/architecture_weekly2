package io.hhplus.architecture.domain.user;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {

    private final Long id;
    private final String name;

    public static User generateUser(
            UserId userId,
            UserName userName) {
        return new User(
                userId.userId,
                userName.userName
        );
    }

    @Value
    public static class UserId {
        Long userId;

        public UserId(Long value) {
            if (value == null || value <= 0) {
                throw new DomainValidationException("userId는 null이거나 0 이하일 수 없습니다.");
            }
            this.userId = value;
        }
    }

    @Value
    public static class UserName {
        String userName;

        public UserName(String value) {
            if (value == null) {
                throw new DomainValidationException("userName은 null일 수 없습니다.");
            }
            this.userName = value;
        }
    }

}

