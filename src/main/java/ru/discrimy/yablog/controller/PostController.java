package ru.discrimy.yablog.controller;

import org.dom4j.rule.Mode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.exceptions.PostNotFoundException;
import ru.discrimy.yablog.exceptions.UnauthorizedDeleteException;
import ru.discrimy.yablog.exceptions.UnauthorizedEditException;
import ru.discrimy.yablog.model.*;
import ru.discrimy.yablog.security.AccessEvaluator;
import ru.discrimy.yablog.security.UserPrincipal;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.PostService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("post")
public class PostController {
    private PostService postService;
    private CommentService commentService;
    private AccessEvaluator accessEvaluator;

    public PostController(PostService postService, CommentService commentService, AccessEvaluator accessEvaluator) {
        this.postService = postService;
        this.commentService = commentService;
        this.accessEvaluator = accessEvaluator;
    }

    @GetMapping("{postId}/show")
    public ModelAndView show(@PathVariable Long postId, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();

        Post post = postService.findById(postId).orElseThrow(PostNotFoundException::new);
        map.put("post", post);
        if (authentication != null) {
            User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

            map.put("isPostUpvoted", post.getUpvotes().stream()
                    .anyMatch(upvote -> upvote.getUser().equals(user)));
            map.put("isPostDownvoted", post.getDownvotes().stream()
                    .anyMatch(downvote -> downvote.getUser().equals(user)));
        }

        return new ModelAndView("post/show", map);
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
    public ModelAndView savePost(Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        return new ModelAndView("post/add", Map.of(
                "post", new Post(
                        "Post title",
                        "# Post header",
                        user
                )));
    }

    @GetMapping("{postId}/edit")
    public ModelAndView savePost(@PathVariable Long postId,
                                 Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!accessEvaluator.hasAccessToPost(authentication, post)) {
            throw new UnauthorizedEditException();
        }

        return new ModelAndView("post/add", Map.of(
                "post", post));
    }

    @PostMapping("add")
    public ModelAndView savePost(@ModelAttribute Post post,
                                 Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        if (!accessEvaluator.hasAccessToPost(authentication, post)) {
            throw new UnauthorizedEditException();
        }

        if (post.getAuthor() == null) {
            post.setAuthor(user);
        }

        Post savedPost = postService.save(post);

        return new ModelAndView("redirect:/post/" + savedPost.getId() + "/show");
    }

    @PostMapping("{postId}/delete")
    public ModelAndView deletePost(@PathVariable Long postId,
                                   Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!accessEvaluator.hasAccessToPost(authentication, post)) {
            throw new UnauthorizedDeleteException();
        }

        postService.delete(post);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("{postId}/upvote")
    public ModelAndView upvote(@PathVariable Long postId,
                               Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postService.undownvote(post, user);
        postService.upvote(post, user);

        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PostMapping("{postId}/unupvote")
    public ModelAndView unupvote(@PathVariable Long postId,
                                 Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postService.unupvote(post, user);

        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PostMapping("{postId}/downvote")
    public ModelAndView downvote(@PathVariable Long postId,
                               Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postService.unupvote(post, user);
        postService.downvote(post, user);

        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PostMapping("{postId}/undownvote")
    public ModelAndView undownvote(@PathVariable Long postId,
                                 Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postService.undownvote(post, user);

        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PostMapping("{postId}/pin")
    public ModelAndView pin(@PathVariable Long postId,
                            Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId).orElseThrow(PostNotFoundException::new);
        if (user.getAuthorities().stream().noneMatch(authority -> authority.getName().equals("ROLE_ADMIN"))) {
            throw new UnauthorizedEditException("User has no privileges to pin post");
        }

        post.setPinned(true);
        postService.save(post);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("{postId}/unpin")
    public ModelAndView unpin(@PathVariable Long postId,
                              Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        Post post = postService.findById(postId).orElseThrow(PostNotFoundException::new);
        if (user.getAuthorities().stream().noneMatch(authority -> authority.getName().equals("ROLE_ADMIN"))) {
            throw new UnauthorizedEditException("User has no privileges to unpin post");
        }

        post.setPinned(false);
        postService.save(post);
        return new ModelAndView("redirect:/");
    }

}
