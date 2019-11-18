package ru.discrimy.yablog.exceptions;

public class UnauthorizedDeleteException extends RuntimeException {
    public UnauthorizedDeleteException() {
    }

    public UnauthorizedDeleteException(String message) {
        super(message);
    }

    public UnauthorizedDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedDeleteException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
