package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.User;

@Component
public interface UserRepository extends BaseRepository<User> {
}
