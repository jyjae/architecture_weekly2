package io.hhplus.architecture.interfaces.api.lecture;


import io.hhplus.architecture.exception.InvalidRequestException;
import lombok.Getter;

import java.time.LocalDateTime;

public record FindAvailableLectureCommand(@Getter Long userId, @Getter LocalDateTime startDate) {

    public FindAvailableLectureCommand {
        if (userId == null || userId <= 0) {
            throw new InvalidRequestException("userId는 null이거나 0 이하일 수 없습니다.");
        }
        if (startDate == null) {
            throw new InvalidRequestException("startDate는 null일 수 없습니다.");
        }
    }
}
