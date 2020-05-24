package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.User;

import java.util.Optional;

@Component
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(uv) FROM Upvote uv WHERE post IN (SELECT p.id FROM Post p WHERE p.author=:user)")
    int getUserTakenUpvotes(User user);

    @Query("SELECT COUNT(dv) FROM Downvote dv WHERE post IN (SELECT p.id FROM Post p WHERE p.author=:user)")
    int getUserTakenDownvotes(User user);
}
