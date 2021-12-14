package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 리뷰 조회시 해당하는 리뷰가 없을 때 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {
        super("Select not found review");
    }
}