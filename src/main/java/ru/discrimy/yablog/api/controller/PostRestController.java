package ru.discrimy.yablog.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.discrimy.yablog.api.converters.PostToPostResponseConverter;
import ru.discrimy.yablog.api.model.ErrorResponse;
import ru.discrimy.yablog.api.model.NewPostRequest;
import ru.discrimy.yablog.api.model.PostResponse;
import ru.discrimy.yablog.exceptions.ResourceNotFoundException;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.security.UserPrincipal;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.TagService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts/")
public class PostRestController {
    private final PostService postService;
    private final TagService tagService;
    private final PostToPostResponseConverter postConverter;

    public PostRestController(PostService postService, TagService tagService, PostToPostResponseConverter postConverter) {
        this.postService = postService;
        this.tagService = tagService;
        this.postConverter = postConverter;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(ResourceNotFoundException exception) {
        return new ErrorResponse(
                "Not Found",
                404L,
                exception.getMessage() != null ? exception.getMessage() : ""
        );
    }

    @GetMapping("all")
    public List<PostResponse> getAll() {
        List<Post> posts = postService.findAll().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt))
                .collect(Collectors.toList());
        return posts.stream()
                .map(postConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("{postId}")
    public PostResponse get(@PathVariable("postId") Long id) {
        Post post = postService.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return postConverter.convert(post);
    }

    @PreAuthorize("hasPermission(null, 'create')")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse create(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody NewPostRequest postRequest) {
        User user = principal.getUser();

        Post post = new Post(
                LocalDateTime.now(),
                postRequest.getTitle(),
                postRequest.getText(),
                user,
                Collections.emptyList(),
                Collections.emptySet(),
                Collections.emptySet(),
                false,
                postRequest.getTags().stream()
                        .map(tagService::findByName)
                        .map(optionalTag -> optionalTag.orElseThrow(ResourceNotFoundException::new))
                        .collect(Collectors.toSet())
        );
        Post savedPost = postService.save(post);
        return postConverter.convert(savedPost);
    }

    @PreAuthorize("hasPermission(#post, 'edit')")
    @PutMapping("{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse edit(
            @RequestBody NewPostRequest postRequest,
            @PathVariable("postId") Post post
    ) {
        if (post.getTitle() != null) {
            post.setTitle(postRequest.getTitle());
        }
        if (postRequest.getText() != null) {
            post.setText(postRequest.getText());
        }
        if (postRequest.getPinned() != null) {
            post.setPinned(postRequest.getPinned());
        }
        if (postRequest.getTags() != null) {
            post.setTags(
                    postRequest.getTags().stream()
                            .map(tagService::findByName)
                            .map(optionalTag -> optionalTag.orElseThrow(ResourceNotFoundException::new))
                            .collect(Collectors.toSet())
            );
        }

        Post savedPost = postService.save(post);
        return postConverter.convert(savedPost);
    }
}
