package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 로그인 중 잘못된 패스워드를 입력했을 때 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Your password is invalid.");
    }
}