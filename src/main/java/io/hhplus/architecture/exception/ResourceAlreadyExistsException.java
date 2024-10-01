package io.hhplus.architecture.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getStatusCode() {
        return "409";
    }
}
