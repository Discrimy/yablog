package ru.discrimy.yablog.service.jpa.service;

import org.springframework.stereotype.Service;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.UserService;
import ru.discrimy.yablog.service.jpa.repository.UserRepository;

@Service
public class UserJpaService extends BaseJpaService<User> implements UserService {
    public UserJpaService(UserRepository repository) {
        super(repository);
    }
}
