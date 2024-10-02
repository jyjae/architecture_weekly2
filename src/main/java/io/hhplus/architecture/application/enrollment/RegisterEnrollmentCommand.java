package io.hhplus.architecture.application.enrollment;

import io.hhplus.architecture.exception.InvalidRequestException;
import lombok.Getter;


public record RegisterEnrollmentCommand(Long userId, Long lectureId) {

    public RegisterEnrollmentCommand {
        if (userId == null || userId <= 0) {
            throw new InvalidRequestException("userId는 null이거나 0 이하일 수 없습니다.");
        }
        if (lectureId == null || lectureId <= 0) {
            throw new InvalidRequestException("lectureId는 null이거나 0 이하일 수 없습니다.");
        }
    }

}
