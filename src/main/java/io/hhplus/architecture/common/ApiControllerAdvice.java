package io.hhplus.architecture.common;

import io.hhplus.architecture.exception.DomainValidationException;
import io.hhplus.architecture.exception.InvalidRequestException;
import io.hhplus.architecture.exception.NotFoundException;
import io.hhplus.architecture.exception.ResourceAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse("500", "에러가 발생했습니다."));
    }

    @ExceptionHandler(value = DomainValidationException.class)
    public ResponseEntity<ErrorResponse> handleDomainValidationException(DomainValidationException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getStatusCode(), e.getMessage()));
    }


}
