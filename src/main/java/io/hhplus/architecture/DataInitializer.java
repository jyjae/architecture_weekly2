package io.hhplus.architecture;

import io.hhplus.architecture.domain.lecture.LectureRepository;
import io.hhplus.architecture.domain.user.UserRepository;
import io.hhplus.architecture.infrastructure.persistence.lecture.LectureJpaEntity;
import io.hhplus.architecture.infrastructure.persistence.user.UserJpaEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    public DataInitializer(LectureRepository lectureRepository, UserRepository userRepository) {
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Lecture 삽입
        LectureJpaEntity lecture1 = LectureJpaEntity.toEntity(null, "자바 기초", "자바 기초에대해서 배운다", "홍길동", 10L, 30L, LocalDateTime.now().plusDays(1));
        LectureJpaEntity lecture2 = LectureJpaEntity.toEntity(null, "스프링부트", "스프링부트 심화 과정", "링링링", 25L, 30L, LocalDateTime.now().plusDays(2));

        lectureRepository.save(lecture1);
        lectureRepository.save(lecture2);

        // User 삽입
        UserJpaEntity user1 = UserJpaEntity.toEntity(null, "유재석");
        UserJpaEntity user2 = UserJpaEntity.toEntity(null, "박명수");

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
