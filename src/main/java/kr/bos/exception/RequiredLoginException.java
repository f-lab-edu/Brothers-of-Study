package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 로그인을 하지않고 로그인이 필요한 서비스에 접근할때 발생하는 exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RequiredLoginException extends RuntimeException {

    public RequiredLoginException() {
        super("This service requires login");
    }
}