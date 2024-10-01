package io.hhplus.architecture.infrastructure.persistence.lecture;

import io.hhplus.architecture.domain.lecture.Lecture;
import io.hhplus.architecture.domain.lecture.LectureRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureMapper lectureMapper;

    public LectureRepositoryImpl(LectureJpaRepository lectureJpaRepository, LectureMapper lectureMapper) {
        this.lectureJpaRepository = lectureJpaRepository;
        this.lectureMapper = lectureMapper;
    }

    /**
     * 강의 ID로 강의를 조회하는 메서드.
     *
     * @param lectureId 조회할 강의의 ID
     * @return Optional로 감싼 Lecture 도메인 객체. 강의가 없을 경우 빈 Optional 반환.
     */
    @Override
    public Optional<Lecture> findById(Long lectureId) {
        // JPA 엔티티를 도메인 엔티티로 변환하여 반환
        return lectureMapper.mapToDomainEntityOptional(lectureJpaRepository.findById(lectureId));
    }

    /**
     * 강의 ID 목록을 제외하고, 시작 시간이 주어진 날짜 이후인 수강 가능한 강의 목록을 조회하는 메서드.
     *
     * @param lectureIds 제외할 강의 ID 목록
     * @param startDate  강의 시작 시간 필터
     * @return 수강 가능한 Lecture 도메인 객체 리스트
     */
    @Override
    public List<Lecture> findAvailableLecturesByInIds(List<Long> lectureIds, LocalDateTime startDate) {
        // 강의 ID 목록과 시작 시간을 기준으로 강의 엔티티를 조회한 후 도메인 객체로 변환
        return lectureJpaRepository.findByIdNotInAndStartTimeAfter(lectureIds, startDate).stream()
                .map(entity -> lectureMapper.mapToDomainEntity(entity))
                .collect(Collectors.toList());
    }

    //테스트용
    @Override
    public void save(LectureJpaEntity lecture) {
        lectureJpaRepository.save(lecture);
    }

    /**
     * 강의 저장하는 메서드.
     *
     * @param lecture 도메인
     * @return
     */
    @Override
    public void save(Lecture lecture) {
        lectureJpaRepository.save(lectureMapper.mapToJpaEntity(lecture));
    }
}
