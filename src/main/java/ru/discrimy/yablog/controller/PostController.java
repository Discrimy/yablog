package ru.discrimy.yablog.controller;

import org.dom4j.rule.Mode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ModelAndView show(
            @PathVariable("postId") Post post,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Map<String, Object> map = new HashMap<>();

        map.put("post", post);
        if (userPrincipal != null) {
            User user = userPrincipal.getUser();
            map.put("isPostUpvoted", post.getUpvotes().stream()
                    .anyMatch(upvote -> upvote.getUser().equals(user)));
            map.put("isPostDownvoted", post.getDownvotes().stream()
                    .anyMatch(downvote -> downvote.getUser().equals(user)));
        }

        return new ModelAndView("post/show", map);
    }

    @PostMapping("{postId}/addcomment")
    public ModelAndView addComment(@PathVariable("postId") Post post,
                                   @RequestParam("text") String commentText,
                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();

        Comment newComment = new Comment(
                post,
                user,
                commentText);
        commentService.save(newComment);

        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(null, 'create')")
    @GetMapping("new")
    public ModelAndView newPost(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();

        return new ModelAndView("post/add", Map.of(
                "post", new Post(
                        "Post title",
                        "# Post header",
                        user
                )));
    }

    @PreAuthorize("hasPermission(#post, 'edit')")
    @GetMapping("{postId}/edit")
    public ModelAndView editPost(@PathVariable("postId") Post post,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ModelAndView("post/add", Map.of(
                "post", post));
    }

    @PreAuthorize("hasPermission(#post, 'edit')")
    @PostMapping("save")
    public ModelAndView savePost(@ModelAttribute Post post,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();

        if (post.getAuthor() == null) {
            post.setAuthor(user);
        }

        Post savedPost = postService.save(post);

        return new ModelAndView("redirect:/post/" + savedPost.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'remove')")
    @PostMapping("{postId}/remove")
    public ModelAndView removePost(@PathVariable("postId") Post post,
                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.delete(post);
        return new ModelAndView("redirect:/");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/upvote")
    public ModelAndView upvote(@PathVariable("postId") Post post,
                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.undownvote(post, user);
        postService.upvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/unupvote")
    public ModelAndView unupvote(@PathVariable("postId") Post post,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.unupvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/downvote")
    public ModelAndView downvote(@PathVariable("postId") Post post,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.unupvote(post, user);
        postService.downvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/undownvote")
    public ModelAndView undownvote(@PathVariable("postId") Post post,
                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.undownvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'pin')")
    @PostMapping("{postId}/pin")
    public ModelAndView pin(@PathVariable("postId") Post post,
                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();

        if (user.getAuthorities().stream().noneMatch(authority -> authority.getName().equals("ROLE_ADMIN"))) {
            throw new UnauthorizedEditException("User has no privileges to pin post");
        }

        post.setPinned(true);
        postService.save(post);
        return new ModelAndView("redirect:/");
    }

    @PreAuthorize("hasPermission(#post, 'unpin')")
    @PostMapping("{postId}/unpin")
    public ModelAndView unpin(@PathVariable("postId") Post post,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        post.setPinned(false);
        postService.save(post);
        return new ModelAndView("redirect:/");
    }

}
