package ru.discrimy.yablog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that code throws when some resource (like Post) not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post not found")
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
    }
}
