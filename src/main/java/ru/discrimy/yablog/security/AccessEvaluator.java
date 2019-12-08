package ru.discrimy.yablog.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;

@Component
public class AccessEvaluator {
    public boolean hasAccessToPost(Authentication authentication, Post post) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        return post.getAuthor().getId().equals(user.getId())
                || user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals("ROLE_ADMIN"));
    }
}
