package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 예약 조회시 해당하는 예약이 없을 때 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SelectReservationNotFoundException extends RuntimeException {

    public SelectReservationNotFoundException() {
        super("Select not found reservation");
    }
}