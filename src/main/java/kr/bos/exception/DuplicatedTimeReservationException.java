package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 중복된 시간으로 예약 생성시. 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedTimeReservationException extends RuntimeException {
    public DuplicatedTimeReservationException() {
        super("This Reservation Time already exists.");
    }
}
