package ru.discrimy.yablog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.exceptions.UserNotFoundException;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("{id}/show")
    public ModelAndView show(
            @PathVariable("id") Long userId,
            @PageableDefault(size = 5, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        User user = userService.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        int givenUpvotes = user.getUpvotes().size();
        int givenDownvotes = user.getDownvotes().size();
        Page<Post> posts = postService.findAllByAuthor(pageable, user);

        return new ModelAndView("user/show", Map.of(
                "user", user,
                "posts", posts,
                "givenUpvotes", givenUpvotes,
                "givenDownvotes", givenDownvotes
        ));
    }
}
