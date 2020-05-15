package ru.discrimy.yablog.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;

import java.io.Serializable;
import java.util.Objects;

@Component
public class AccessEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object object, Object permObject) {
        User user = (
                   auth == null
                || auth.getPrincipal() == null
                || auth instanceof AnonymousAuthenticationToken)
                ? null
                : ((UserPrincipal) auth.getPrincipal()).getUser();
        if (user == null) {
            return false;
        }
        String permission = ((String) permObject);

        if (object == null) {
            return true;
        }
        if (object instanceof Post) {
            Post post = ((Post) object);
            return this.hasPermissionToPost(user, post, permission);
        }

        throw new UnsupportedOperationException(
                "Unknown object type: " + object.getClass().getCanonicalName());
    }

    private boolean hasPermissionToPost(User user, Post post, String permission) {
        switch (permission) {
            case "show":
            case "vote":
                return true;
            case "pin":
            case "unpin":
                return user.getAuthorities().stream()
                        .anyMatch(authority -> authority.getName().equals("ROLE_ADMIN"));
            default:
                return Objects.equals(post.getAuthor(), user) || user.getAuthorities().stream()
                        .anyMatch(authority -> authority.getName().equals("ROLE_ADMIN"));
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException(
                "hasPermission() by ID is not supported");
    }
}
