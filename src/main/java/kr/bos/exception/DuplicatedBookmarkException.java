package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 이미 북 마크 등록되어 있을 때 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedBookmarkException extends RuntimeException {

    public DuplicatedBookmarkException() {
        super("bookmark is already set.");
    }
}