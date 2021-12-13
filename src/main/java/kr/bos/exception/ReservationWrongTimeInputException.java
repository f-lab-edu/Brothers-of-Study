package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 잘못된 예약 시간 입력시 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReservationWrongTimeInputException extends RuntimeException {

    public ReservationWrongTimeInputException() {
        super("Please check your reservation time again.");
    }
}