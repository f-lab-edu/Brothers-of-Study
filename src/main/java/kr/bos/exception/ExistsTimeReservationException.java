package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 이미 존재하는 예약이 있을 때 발생하는 Exception.
 * <br>
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistsTimeReservationException extends RuntimeException {

    public ExistsTimeReservationException() {
        super("A reservation exists at that time.");
    }
}
