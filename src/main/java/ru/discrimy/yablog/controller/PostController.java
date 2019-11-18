package ru.discrimy.yablog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.exceptions.PostNotFoundException;
import ru.discrimy.yablog.exceptions.UnauthorizedDeleteException;
import ru.discrimy.yablog.exceptions.UnauthorizedEditException;
import ru.discrimy.yablog.model.Comment;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.security.UserPrincipal;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.PostService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

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
                "post", postService.findById(postId)
                        .orElseThrow(PostNotFoundException::new)));
    }

    @PostMapping("{postId}/addcomment")
    public ModelAndView addComment(@PathVariable Long postId,
                                   @RequestParam("text") String commentText,
                                   Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Comment newComment = new Comment(
                postService.findById(postId).orElseThrow(PostNotFoundException::new),
                user,
                commentText);
        commentService.save(newComment);

        return new ModelAndView("redirect:/post/" + postId + "/show");
    }

    @GetMapping("add")
    public ModelAndView savePost() {
        return new ModelAndView("post/add", Map.of(
                "post", new Post(
                        "Post title",
                        "# Post header",
                        null,
                        new ArrayList<>()
                )));
    }

    @GetMapping("add/{postId}")
    public ModelAndView savePost(@PathVariable Long postId,
                                 Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!postService.hasAuthorityToEdit(user, post)) {
            throw new UnauthorizedEditException();
        }

        return new ModelAndView("post/add", Map.of(
                "post", post));
    }

    @PostMapping("add")
    public ModelAndView savePost(@ModelAttribute Post post,
                                 Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        if (!postService.hasAuthorityToEdit(user, post)) {
            throw new UnauthorizedEditException();
        }

        post.setAuthor(user);
        Post savedPost = postService.save(post);

        return new ModelAndView("redirect:/post/" + savedPost.getId() + "/show");
    }

    @PostMapping("{postId}/delete")
    public ModelAndView deletePost(@PathVariable Long postId,
                                   Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!postService.hasAuthorityToDelete(user, post)) {
            throw new UnauthorizedDeleteException();
        }

        postService.delete(post);
        return new ModelAndView("redirect:/");
    }
}
