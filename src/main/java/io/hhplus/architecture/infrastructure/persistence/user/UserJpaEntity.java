package io.hhplus.architecture.infrastructure.persistence.user;


import io.hhplus.architecture.infrastructure.persistence.enrollment.EnrollmentJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder
    public UserJpaEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserJpaEntity toEntity(
            Long id,
            String name
    ) {
        return UserJpaEntity.builder()
                .id(id)
                .name(name)
                .build();
    }
}
