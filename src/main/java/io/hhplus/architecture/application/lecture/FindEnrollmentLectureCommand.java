package io.hhplus.architecture.application.lecture;

import io.hhplus.architecture.exception.InvalidRequestException;
import lombok.Getter;

public record FindEnrollmentLectureCommand(Long userId) {

    public FindEnrollmentLectureCommand {
        if (userId == null || userId <= 0) {
            throw new InvalidRequestException("userId는 null이거나 0 이하일 수 없습니다.");
        }
    }
}
