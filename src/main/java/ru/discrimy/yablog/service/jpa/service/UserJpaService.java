package ru.discrimy.yablog.service.jpa.service;

import org.springframework.stereotype.Service;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.UserService;
import ru.discrimy.yablog.service.jpa.repository.UserRepository;

import java.util.Optional;

@Service
public class UserJpaService extends BaseJpaService<User> implements UserService {
    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public int getUserScore(User user) {
        return userRepository.getUserTakenUpvotes(user)
                - userRepository.getUserTakenDownvotes(user);
    }
}
