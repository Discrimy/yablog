package ru.discrimy.yablog.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Aspect annotation that require Post argument of method to be not null,
 * otherwise throws ResourceNotFoundException
 * Method must have Post argument
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredPost {

}
