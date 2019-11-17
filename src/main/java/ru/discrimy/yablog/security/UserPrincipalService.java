package ru.discrimy.yablog.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.discrimy.yablog.service.UserService;

@Service
public class UserPrincipalService implements UserDetailsService {
    private UserService userService;

    public UserPrincipalService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new UserPrincipal(userService.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User " + s + " does not exist")));
    }
}
