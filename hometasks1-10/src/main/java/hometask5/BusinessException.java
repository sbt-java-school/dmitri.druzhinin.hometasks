package hometask5;

/**
 * This exception signals that user has the wrong behavior. The message of this exception can be shown to user.
 */
public class BusinessException extends Exception {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
