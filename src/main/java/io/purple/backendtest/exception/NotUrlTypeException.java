package io.purple.backendtest.exception;

public class NotUrlTypeException extends RuntimeException {
    public NotUrlTypeException() {
        super();
    }

    public NotUrlTypeException(String message) {
        super(message);
    }

    public NotUrlTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUrlTypeException(Throwable cause) {
        super(cause);
    }

    protected NotUrlTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
