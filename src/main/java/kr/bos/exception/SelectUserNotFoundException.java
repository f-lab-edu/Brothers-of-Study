package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 유저 조회시 해당하는 유저가 없을 때 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SelectUserNotFoundException extends RuntimeException {
    public SelectUserNotFoundException() {
        super("Select not found user");
    }
}
