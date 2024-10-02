package io.hhplus.architecture.application.enrollment;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.exception.InvalidRequestException;
import io.hhplus.architecture.exception.ResourceAlreadyExistsException;
import io.hhplus.architecture.infrastructure.persistence.enrollment.EnrollmentRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EnrollmentFacadeServiceTest {

    @Autowired
    private EnrollmentFacadeService enrollmentFacadeService;

    @Autowired
    private EnrollmentRepositoryImpl enrollmentRepository;

    @Sql({"/database_reset.sql", "/user_lecture_insert.sql"})
    @DisplayName("동시에 40명 수강신청 시 30명만 수강신청 성공")
    @Test
    void shouldAllowOnly30EnrollmentsWhen40UsersEnrollSimultaneously() throws ExecutionException, InterruptedException {
        // Given

        // When
        // 병렬로 실행할 CompletableFuture 목록
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        // 성공한 수강신청의 결과와 예외가 발생한 경우를 구분
        for (int i = 0; i < 40; i++) {
            int finalI = i;
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    RegisterEnrollmentCommand command = new RegisterEnrollmentCommand(finalI + 1L, 1L);
                    enrollmentFacadeService.enrollment(command);
                    return true;
                } catch (InvalidRequestException e) {
                    return false;
                }
            });
            futures.add(future);
        }

        // 모든 비동기 작업이 완료될 때까지 대기
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get(); // 모든 작업이 끝날 때까지 기다림

        // Then
        // CompletableFuture 결과에서 성공한 수강신청과 실패한 수강신청을 구분
        List<Boolean> successfulEnrollments = futures.stream()
                .map(CompletableFuture::join)
                .filter(result -> result).collect(Collectors.toList());

        long failedEnrollments = futures.stream()
                .map(CompletableFuture::join)
                .filter(result -> !result) // false인 값만 필터링
                .count();

        // 성공한 수강신청은 30명
        assertThat(successfulEnrollments.size()).isEqualTo(30L);
        // 실패한 수강신청은 10명
        assertThat(failedEnrollments).isEqualTo(10L);

        // DB에 실제로 저장된 수강신청 내역도 30개인지 확인
        List<Enrollment> enrollments = enrollmentRepository.findByLectureId(1L);
        assertThat(enrollments.size()).isEqualTo(30L);
    }

    @Sql({"/database_reset.sql", "/user_lecture_insert.sql"})
    @DisplayName("동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공")
    @Test
    void shouldEnrollInLectureOnceWhenUserAttemptsMultipleTimes() throws ExecutionException, InterruptedException {
        // Given

        // When
        // 병렬로 실행할 CompletableFuture 목록
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        // 성공한 수강신청의 결과와 예외가 발생한 경우를 구분
        for (int i = 0; i < 5; i++) {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    RegisterEnrollmentCommand command = new RegisterEnrollmentCommand(1L, 1L);
                    enrollmentFacadeService.enrollment(command);
                    return true; // 성공 시 true 반환
                } catch (ResourceAlreadyExistsException e) {
                    return false; // 예외 발생 시 false 반환
                }
            });
            futures.add(future);
        }

        // 모든 비동기 작업이 완료될 때까지 대기
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get(); // 모든 작업이 끝날 때까지 기다림

        // Then
        // CompletableFuture 결과에서 성공한 수강신청과 실패한 수강신청을 구분
        long successfulEnrollments = futures.stream()
                .map(CompletableFuture::join)
                .filter(result -> result) // true인 값만 필터링
                .count();

        long failedEnrollments = futures.stream()
                .map(CompletableFuture::join)
                .filter(result -> !result) // false인 값만 필터링
                .count();

        // 성공한 수강신청은 1명
        assertThat(successfulEnrollments).isEqualTo(1L);
        // 실패한 수강신청은 4명
        assertThat(failedEnrollments).isEqualTo(4L);

        assertThat(enrollmentRepository.findByUserIdAndLectureId(1L, 1L)).isNotNull();
    }
}
