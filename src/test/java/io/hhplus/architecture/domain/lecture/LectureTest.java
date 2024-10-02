package io.hhplus.architecture.domain.lecture;

import io.hhplus.architecture.exception.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class LectureTest {

    @DisplayName("Lecture 생성 성공")
    @Test
    void shouldCreateLectureSuccessfully() {
        // Given
        Lecture.LectureId lectureId = new Lecture.LectureId(1L);
        Lecture.Title title = new Lecture.Title("자바 프로그래밍");
        Lecture.Description description = new Lecture.Description("자바에 대해서 배운다.");
        Lecture.Instructor instructor = new Lecture.Instructor("홍길동");
        Lecture.EnrollmentCount enrollmentCount = new Lecture.EnrollmentCount(10L);
        Lecture.Capacity capacity = new Lecture.Capacity(30L);
        Lecture.StartTime startTime = new Lecture.StartTime(LocalDateTime.now().plusDays(1));

        // When
        Lecture lecture = Lecture.generateLecture(lectureId, title, description, instructor, enrollmentCount, capacity, startTime);

        // Then
        assertThat(lecture.getId()).isEqualTo(1L);
        assertThat(lecture.getTitle()).isEqualTo("자바 프로그래밍");
    }

    @DisplayName("Lecture 생성 실패 - ID가 유효하지 않음")
    @Test
    void shouldFailWhenLectureIdIsInvalid() {
        assertThatThrownBy(() -> new Lecture.LectureId(null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("강의 ID는 0보다 커야 합니다.");

        assertThatThrownBy(() -> new Lecture.LectureId(0L))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("강의 ID는 0보다 커야 합니다.");
    }

    @DisplayName("Lecture 생성 실패 - 제목이 비어있음")
    @Test
    void shouldFailWhenLectureTitleIsInvalid() {
        assertThatThrownBy(() -> new Lecture.Title(null))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("강의 제목은 비어있을 수 없습니다.");

        assertThatThrownBy(() -> new Lecture.Title(""))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("강의 제목은 비어있을 수 없습니다.");
    }

    @DisplayName("수강 인원이 30명 이하인 경우 수강 신청 가능")
    @Test
    void shouldBeAvailableForEnrollmentWhenEnrollmentCountIsLessThan30() {
        // Given
        Lecture.LectureId lectureId = new Lecture.LectureId(1L);
        Lecture.Title title = new Lecture.Title("Java Programming");
        Lecture.Description description = new Lecture.Description("Learn Java");
        Lecture.Instructor instructor = new Lecture.Instructor("John Doe");
        Lecture.EnrollmentCount enrollmentCount = new Lecture.EnrollmentCount(10L);
        Lecture.Capacity capacity = new Lecture.Capacity(30L);
        Lecture.StartTime startTime = new Lecture.StartTime(LocalDateTime.now().plusDays(1));

        // When
        Lecture lecture = Lecture.generateLecture(lectureId, title, description, instructor, enrollmentCount, capacity, startTime);

        // When
        boolean available = lecture.isAvailableForEnrollment();

        // Then
        assertThat(available).isTrue();
    }
}
