package kr.bos.exception;

/**
 * 403 허용하지 않은 유저가 접근시 발생하는 Exception.
 *
 * @since 1.0.0
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String msg) {
        super(msg);
    }
}