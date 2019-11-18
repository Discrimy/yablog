package ru.discrimy.yablog.exceptions;

public class UnauthorizedEditException extends RuntimeException {
    public UnauthorizedEditException() {
    }

    public UnauthorizedEditException(String message) {
        super(message);
    }

    public UnauthorizedEditException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedEditException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedEditException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
