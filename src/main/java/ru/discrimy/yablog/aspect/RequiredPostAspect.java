package ru.discrimy.yablog.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.exceptions.ResourceNotFoundException;
import ru.discrimy.yablog.model.Post;

/**
 * RequiredPost aspect implementation
 * @see RequiredPost
 */
@Aspect
@Component
public class RequiredPostAspect {
    @Before("execution(@RequiredPost * *(..)) && args(post, ..)")
    public void aroundMethodWithInjectPost(Post post) {
        if (post == null) {
            throw new ResourceNotFoundException();
        }
    }

}
