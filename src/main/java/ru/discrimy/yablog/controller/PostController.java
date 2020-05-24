package ru.discrimy.yablog.controller;

import org.dom4j.rule.Mode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.discrimy.yablog.aspect.RequiredPost;
import ru.discrimy.yablog.exceptions.ResourceNotFoundException;
import ru.discrimy.yablog.model.Comment;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.security.UserPrincipal;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.PostService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("{postId}/show")
    @RequiredPost
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
    @RequiredPost
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
    @RequiredPost
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
    @RequiredPost
    public ModelAndView editPost(@PathVariable("postId") Post post) {
        return new ModelAndView("post/add", Map.of(
                "post", post));
    }

    @PreAuthorize("hasPermission(#post, 'edit')")
    @PostMapping("save")
    @RequiredPost
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
    @RequiredPost
    public ModelAndView removePost(@PathVariable("postId") Post post) {
        postService.delete(post);
        return new ModelAndView("redirect:/");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/upvote")
    @RequiredPost
    public ModelAndView upvote(@PathVariable("postId") Post post,
                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.undownvote(post, user);
        postService.upvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/unupvote")
    @RequiredPost
    public ModelAndView unupvote(@PathVariable("postId") Post post,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.unupvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/downvote")
    @RequiredPost
    public ModelAndView downvote(@PathVariable("postId") Post post,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.unupvote(post, user);
        postService.downvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'vote')")
    @PostMapping("{postId}/undownvote")
    @RequiredPost
    public ModelAndView undownvote(@PathVariable("postId") Post post,
                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        postService.undownvote(post, user);
        return new ModelAndView("redirect:/post/" + post.getId() + "/show");
    }

    @PreAuthorize("hasPermission(#post, 'pin')")
    @PostMapping("{postId}/pin")
    @RequiredPost
    public ModelAndView pin(@PathVariable("postId") Post post) {
        post.setPinned(true);
        postService.save(post);
        return new ModelAndView("redirect:/");
    }

    @PreAuthorize("hasPermission(#post, 'unpin')")
    @PostMapping("{postId}/unpin")
    @RequiredPost
    public ModelAndView unpin(@PathVariable("postId") Post post) {
        post.setPinned(false);
        postService.save(post);
        return new ModelAndView("redirect:/");
    }

}
