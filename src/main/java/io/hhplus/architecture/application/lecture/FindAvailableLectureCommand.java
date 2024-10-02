package io.hhplus.architecture.application.lecture;


import io.hhplus.architecture.exception.InvalidRequestException;
import lombok.Getter;

import java.time.LocalDateTime;

public record FindAvailableLectureCommand(Long userId, LocalDateTime startDate) {

    public FindAvailableLectureCommand {
        if (userId == null || userId <= 0) {
            throw new InvalidRequestException("userId는 null이거나 0 이하일 수 없습니다.");
        }
        if (startDate == null) {
            throw new InvalidRequestException("startDate는 null일 수 없습니다.");
        }
    }
}
