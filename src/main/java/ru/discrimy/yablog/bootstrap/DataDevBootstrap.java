package ru.discrimy.yablog.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Comment;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;

@Slf4j
@Component
public class DataDevBootstrap implements CommandLineRunner {
    private PostService postService;
    private CommentService commentService;
    private UserService userService;

    public DataDevBootstrap(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("Loading test data...");

        User admin = new User("admin", "hashed_password", new HashSet<>());
        User user1 = new User("user1", "hashed_1234", new HashSet<>());

        Post post1 = new Post("First post!", "BODY TEXT", admin, new ArrayList<>());
        Post post2 = new Post("Second post", "YEAH", admin, new ArrayList<>());

        Comment comment11 = new Comment(post1, user1, "Awo");

        userService.save(admin);
        userService.save(user1);

        postService.save(post1);
        postService.save(post2);

        commentService.save(comment11);

        log.debug("Loading finished");
    }
}
