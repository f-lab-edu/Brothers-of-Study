package kr.bos.utils;

import java.time.LocalDateTime;
import kr.bos.exception.AccessDeniedException;
import kr.bos.exception.DuplicatedException;
import kr.bos.exception.NotFoundException;
import kr.bos.exception.RequiredLoginException;
import lombok.Builder;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler. 애플리케이션 전역에서 발생하는 예외 처리.
 *
 * @since 1.0.0
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * BadRequest 400 Exception Handler.
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> badRequestHandler(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .errorMessage(e.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Unauthorized 401 Exception Handler.
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = {RequiredLoginException.class})
    protected ResponseEntity<ErrorResponse> unauthorizedHandler(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.UNAUTHORIZED.value())
            .errorMessage(e.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Forbidden 403 Exception Handler.
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<ErrorResponse> forbiddenHandler(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.FORBIDDEN.value())
            .errorMessage(e.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * NotFound 404 Exception Handler.
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorResponse> notFoundHandler(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .errorMessage(e.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Conflict 409 Exception Handler.
     *
     * @since 1.0.0
     */
    @ExceptionHandler(value = {DuplicatedException.class})
    protected ResponseEntity<ErrorResponse> conflictHandler(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.CONFLICT.value())
            .errorMessage(e.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * 에러의 정보를 담을 ErrorResponse DTO.
     *
     * @since 1.0.0
     */
    @Value
    @Builder
    public static class ErrorResponse {

        LocalDateTime timestamp;
        Integer status;
        String errorMessage;
    }
}
