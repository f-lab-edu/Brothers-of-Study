package kr.bos.exception;

/**
 * 404 Not Found Exception.
 *
 * @since 1.0.0
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}
