package io.hhplus.architecture.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DomainValidationException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public DomainValidationException(String message) {
        super(message);
    }

    public DomainValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getStatusCode() {
        return "400";
    }
}
