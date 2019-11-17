package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.User;

import java.util.Optional;

@Component
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
}
