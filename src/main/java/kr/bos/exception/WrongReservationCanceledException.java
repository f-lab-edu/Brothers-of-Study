package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 시작 시간 10분 전에 취소 요청을 하는 경우 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongReservationCanceledException extends RuntimeException {

    public WrongReservationCanceledException() {
        super("Reservations can not be canceled 10 minutes.");
    }
}