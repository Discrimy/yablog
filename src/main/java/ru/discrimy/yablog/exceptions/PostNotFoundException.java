package ru.discrimy.yablog.exceptions;

import ru.discrimy.yablog.model.Post;

public class PostNotFoundException extends ResourceNotFoundException {
    public PostNotFoundException() {
    }

    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(Throwable cause) {
        super(cause);
    }

    public PostNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PostNotFoundException(Post post) {

    }
}
