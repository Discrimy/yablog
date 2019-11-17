package ru.discrimy.yablog.service;

import ru.discrimy.yablog.model.User;

import java.util.Optional;

public interface UserService extends BaseService<User> {
    Optional<User> findByUsername(String username);
}
