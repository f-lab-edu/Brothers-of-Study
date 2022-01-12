package kr.bos.exception;

/**
 * 409 중복으로 인해 발생하는 Exception.
 *
 * @since 1.0.0
 */
public class DuplicatedException extends RuntimeException {

    public DuplicatedException(String msg) {
        super(msg);
    }
}
