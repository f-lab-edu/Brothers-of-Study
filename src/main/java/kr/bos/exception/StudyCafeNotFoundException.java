package kr.bos.exception;

/**
 * Runtime exception for StudyCafe not found cases.
 *
 * @since 1.0.0
 */
public class StudyCafeNotFoundException extends RuntimeException {
    public StudyCafeNotFoundException(Long id) {
        super("There is no matching Study Cafe with study cafe id: " + id);
    }

    public StudyCafeNotFoundException(String studyCafeName) {
        super("Study Cafe: " + studyCafeName + " was not founded.");
    }
}
