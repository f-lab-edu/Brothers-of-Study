package kr.bos.utils;

import java.time.LocalDateTime;
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

        log.error(e.getClass().getName(), e);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
