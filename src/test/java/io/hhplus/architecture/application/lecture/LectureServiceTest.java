package io.hhplus.architecture.application.lecture;

import io.hhplus.architecture.domain.enrollment.Enrollment;
import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.exception.NotFoundException;
import io.hhplus.architecture.infrastructure.persistence.lecture.LectureRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;  // AssertJ
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepositoryImpl lectureRepository;

    private List<Lecture> lectures = new ArrayList<>();

    private List<Lecture> inAvailableLectures = new ArrayList<>();

    private List<Enrollment> enrollments;

    @BeforeEach
    void setup() {
        lectures.add(
                Lecture.generateLecture(new Lecture.LectureId(2L),
                        new Lecture.Title("컴퓨터 구조"),
                        new Lecture.Description("컴퓨터 구조에 대해서 배웁니다."),
                        new Lecture.Instructor("흥부"),
                        new Lecture.EnrollmentCount(30L),
                        new Lecture.Capacity(30L),
                        new Lecture.StartTime(LocalDateTime.of(2024, 10, 1, 9, 30))));

        lectures.add(
                Lecture.generateLecture(new Lecture.LectureId(3L),
                        new Lecture.Title("운영체제"),
                        new Lecture.Description("운영체제에 대해서 배웁니다."),
                        new Lecture.Instructor("홍운영"),
                        new Lecture.EnrollmentCount(0L),
                        new Lecture.Capacity(30L),
                        new Lecture.StartTime(LocalDateTime.of(2024, 10, 1, 9, 30))));
        lectures.add(
                Lecture.generateLecture(new Lecture.LectureId(4L),
                        new Lecture.Title("인공지능"),
                        new Lecture.Description("인공지능에 대해서 배웁니다."),
                        new Lecture.Instructor("홍인공"),
                        new Lecture.EnrollmentCount(0L),
                        new Lecture.Capacity(30L),
                        new Lecture.StartTime(LocalDateTime.of(2024, 10, 1, 9, 30))));
        inAvailableLectures.add(
                Lecture.generateLecture(new Lecture.LectureId(5L),
                        new Lecture.Title("자바 프로그래밍"),
                        new Lecture.Description("자바에 대해서 배웁니다."),
                        new Lecture.Instructor("자바바"),
                        new Lecture.EnrollmentCount(30L),
                        new Lecture.Capacity(30L),
                        new Lecture.StartTime(LocalDateTime.of(2024, 10, 1, 9, 30))));

        inAvailableLectures.add(
                Lecture.generateLecture(new Lecture.LectureId(7L),
                        new Lecture.Title("운영체제"),
                        new Lecture.Description("운영체제에 대해서 배웁니다."),
                        new Lecture.Instructor("홍운영"),
                        new Lecture.EnrollmentCount(30L),
                        new Lecture.Capacity(30L),
                        new Lecture.StartTime(LocalDateTime.of(2024, 10, 1, 9, 30))));
        inAvailableLectures.add(
                Lecture.generateLecture(new Lecture.LectureId(8L),
                        new Lecture.Title("인공지능"),
                        new Lecture.Description("인공지능에 대해서 배웁니다."),
                        new Lecture.Instructor("홍인공"),
                        new Lecture.EnrollmentCount(30L),
                        new Lecture.Capacity(30L),
                        new Lecture.StartTime(LocalDateTime.of(2024, 10, 1, 9, 30))));
        inAvailableLectures.add(
                Lecture.generateLecture(new Lecture.LectureId(9L),
                        new Lecture.Title("자바 프로그래밍"),
                        new Lecture.Description("자바에 대해서 배웁니다."),
                        new Lecture.Instructor("자바바"),
                        new Lecture.EnrollmentCount(30L),
                        new Lecture.Capacity(30L),
                        new Lecture.StartTime(LocalDateTime.of(2024, 10, 1, 9, 30))));

        LocalDateTime localDateTime = LocalDateTime.now();
        enrollments = List.of(
                Enrollment.generateEnrollment(
                        new Enrollment.UserId(1L),
                        new Enrollment.LectureId(1L),
                        new Enrollment.EnrollmentDate(localDateTime))
        );
    }

    @DisplayName("수강신청 가능한 강의 조회")
    @Test
    void findAvailableLecturesSuccessfully() {
        // Given
        when(lectureRepository.findAvailableLecturesByInIds(List.of(1L), LocalDateTime.of(2024, 8, 1, 9, 30))).thenReturn(lectures);
        // When
        List<Lecture> availableLectures = lectureService.findAvailableLecturesExcluding(List.of(1L), LocalDateTime.of(2024, 8, 1, 9, 30));

        // Then
        assertThat(availableLectures.size()).isEqualTo(2L);
//        assertThat(availableLectures.get(0).getId()).isEqualTo(2L); // 30명 참
        assertThat(availableLectures.get(0).getId()).isEqualTo(3L);
        assertThat(availableLectures.get(1).getId()).isEqualTo(4L);

    }

    @DisplayName("수강신청 가능한 강의 빈 값 조회")
    @Test
    void shouldReturnEmptyListIfNoLecturesAvailableForEnrollment() {
        // Given
        when(lectureRepository.findAvailableLecturesByInIds(List.of(1L), LocalDateTime.of(2024, 8, 1, 9, 30))).thenReturn(inAvailableLectures);
        // When
        List<Lecture> availableLectures = lectureService.findAvailableLecturesExcluding(List.of(1L), LocalDateTime.of(2024, 8, 1, 9, 30));
        // Then
        assertThat(availableLectures.size()).isEqualTo(0L);
    }

    @DisplayName("강의가 존재하지 않은 경우 예외 발생")
    @Test
    void shouldFailWhenLectureNotFound() {
        // Given

        when(lectureRepository.findById(1L)).thenReturn(Optional.empty());
        // When

        // Then
        assertThatThrownBy(() -> lectureService.findByIdWithLock(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("강의가 존재하지 않습니다.");
    }
}