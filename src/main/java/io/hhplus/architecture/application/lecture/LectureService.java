package io.hhplus.architecture.application.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.lecture.LectureRepository;
import io.hhplus.architecture.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    /**
     * 수강 신청 가능한 강의를 조회하는 메서드 (강의 ID를 제외한).
     *
     * @param lectureIds 제외할 강의 ID 목록
     * @param startDate  강의 시작 날짜 기준
     * @return 수강신청 가능한 강의 목록
     */
    public List<Lecture> findAvailableLecturesExcluding(List<Long> lectureIds, LocalDateTime startDate) {
        // 강의 조회
        List<Lecture> lectures = lectureRepository.findAvailableLecturesByInIds(lectureIds, startDate);

        // 수강신청 불가 강의 필터링
        List<Lecture> deleteLectures = new ArrayList<>();
        for (Lecture lecture : lectures) {
            if (!lecture.isAvailableForEnrollment()) {
                deleteLectures.add(lecture);
            }
        }

        // 수강신청 불가 강의 제거 (상태 변화)
        for (Lecture lecture : deleteLectures) {
            lectures.remove(lecture);
        }

        return lectures;
    }

    /**
     * 강의 ID로 강의를 조회하는 메서드.
     *
     * @param lectureId 강의 ID
     * @return 강의 도메인 객체
     */
    @Transactional
    public Lecture findByIdWithLock(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NotFoundException("강의가 존재하지 않습니다."));
    }

    /**
     * 수강신청한 인원 증가시키는 메서드
     */
    @Transactional
    public void increaseEnrollmentCount(Lecture lecture) {
        lecture.increaseEnrollmentCount();
        lectureRepository.save(lecture);
    }
}
