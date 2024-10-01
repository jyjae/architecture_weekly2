package io.hhplus.architecture.application.enrollment;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.user.User;
import io.hhplus.architecture.exception.ResourceAlreadyExistsException;
import io.hhplus.architecture.infrastructure.persistence.enrollment.EnrollmentRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;  // AssertJ
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentService enrollmentService;
    @Mock
    private EnrollmentRepositoryImpl repository;

    private User user;
    private Lecture lecture;
    private Enrollment enrollment;


    @BeforeEach
    void setUp() {
       user = User.generateUser(new User.UserId(1L), new User.UserName("홍길동"));
       lecture = Lecture.generateLecture(new Lecture.LectureId(1L),
                new Lecture.Title("자료 구조"),
                new Lecture.Description("자료 구조에 대해서 배웁니다."),
                new Lecture.Instructor("홍길동"),
                new Lecture.EnrollmentCount(29L),
                new Lecture.StartTime(LocalDateTime.now()));

        LocalDateTime localDateTime = LocalDateTime.now();
        enrollment = Enrollment.generateEnrollment(
                new Enrollment.UserId(1L),
                new Enrollment.LectureId(1L),
                new Enrollment.LectureTitle("자료구조"),
                new Enrollment.Instructor("홍길동"),
                new Enrollment.EnrollmentDate(localDateTime));
    }

    @DisplayName("userId가 해당 강의를 수강신청했는지 조회 true 반환")
    @Test
    void shouldReturnTrueWhenUserHasEnrolledInLecture() {
        // Given
        when(repository.isEnrolled(1L, 1L)).thenReturn(true);

        // When
        boolean isEnrolled = enrollmentService.isEnrolled(1L, 1L);

        // Then
        assertThat(isEnrolled).isTrue();
    }

    @DisplayName("수강 신청 성공")
    @Test
    void shouldSuccessfullyEnrollUserInLecture() {
        // Given

        when(repository.isEnrolled(user.getId(), lecture.getId())).thenReturn(false);
        when(repository.enrollment(user, lecture)).thenReturn(enrollment);

        // When
        Enrollment savedEnrollment = enrollmentService.enrollment(user, lecture);

        // Then
        assertThat(savedEnrollment.getUserId()).isEqualTo(1L);
        assertThat(savedEnrollment.getLectureId()).isEqualTo(1L);
    }

    @DisplayName("수강 신청 실패")
    @Test
    void shouldFailUserInLecture() {
        // Given

        when(repository.isEnrolled(user.getId(), lecture.getId())).thenReturn(true);

        // When

        // Then
        assertThatThrownBy(() -> enrollmentService.enrollment(user, lecture))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessage("이미 수강신청 했습니다.");
    }

    @DisplayName("유저가 수강신청한 특강 목록 조회 성공")
    @Test
    void shouldFindEnrolledLecturesForUser() {
        // Given
        when(repository.findByUserId(1L)).thenReturn(List.of(enrollment));
        // When
        List<Enrollment> enrollments =  enrollmentService.findUserEnrolledLectures(1L);

        // Then
        assertThat(enrollments.size()).isEqualTo(1L);
    }


}