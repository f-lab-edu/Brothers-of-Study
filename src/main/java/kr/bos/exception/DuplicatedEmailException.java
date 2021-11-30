package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 중복된 이메일로 회원가입시 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException(String message) {
        super(message);
    }
}