package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 스터디 카페 조회시 해당하는 스터디 카페가 없을 때 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SelectStudyCafeNotFoundException extends RuntimeException {

    public SelectStudyCafeNotFoundException() {
        super("Select not found study cafe.");
    }
}