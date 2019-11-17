package ru.discrimy.yablog.service.jpa.service;

import org.springframework.stereotype.Service;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.UserService;
import ru.discrimy.yablog.service.jpa.repository.UserRepository;

import java.util.Optional;

@Service
public class UserJpaService extends BaseJpaService<User> implements UserService {
    public UserJpaService(UserRepository repository) {
        super(repository);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return ((UserRepository) repository).findByUsername(username);
    }
}
