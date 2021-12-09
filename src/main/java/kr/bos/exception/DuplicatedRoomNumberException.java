package kr.bos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 중복된 방 번호로 생성시 발생하는 Exception.
 *
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedRoomNumberException extends RuntimeException {

    public DuplicatedRoomNumberException() {
        super("This room number already exists.");
    }
}
