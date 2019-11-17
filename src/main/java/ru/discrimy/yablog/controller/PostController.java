package ru.discrimy.yablog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.model.Comment;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.security.UserPrincipal;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.PostService;

import java.util.Collections;

@Controller
@RequestMapping("post")
public class PostController {
    private PostService postService;
    private CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("{postId}/show")
    public ModelAndView show(@PathVariable Long postId) {
        return new ModelAndView("post/show", Collections.singletonMap(
                "post", postService.findById(postId).orElseThrow(RuntimeException::new)));
    }

    @PostMapping("{postId}/addcomment")
    public ModelAndView addComment(@PathVariable Long postId,
                                   @RequestParam("text") String commentText,
                                   Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Comment newComment = new Comment(
                postService.findById(postId).orElseThrow(RuntimeException::new),
                user,
                commentText);
        commentService.save(newComment);

        return new ModelAndView("redirect:/post/" + postId + "/show");
    }
}
