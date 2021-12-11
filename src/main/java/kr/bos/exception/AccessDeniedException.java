package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 허용하지 않은 유저가 접근시 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("The service cannot be accessed.");
    }
}