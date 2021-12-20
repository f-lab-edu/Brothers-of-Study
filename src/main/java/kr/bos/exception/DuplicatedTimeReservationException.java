package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 이미 중복된 예약 시간에 예약했을 때 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedTimeReservationException extends RuntimeException {

    public DuplicatedTimeReservationException() {
        super("This room has already been reserved.");
    }
}
