package ru.discrimy.yablog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.discrimy.yablog.api.converters.PostToPostResponseConverter;
import ru.discrimy.yablog.exceptions.PostNotFoundException;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.api.model.PostResponse;
import ru.discrimy.yablog.service.PostService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts/")
public class PostRestController {
    private final PostService postService;
    private final PostToPostResponseConverter postConverter;

    public PostRestController(PostService postService, PostToPostResponseConverter postConverter) {
        this.postService = postService;
        this.postConverter = postConverter;
    }

    @GetMapping("all")
    public List<PostResponse> findAll() {
        List<Post> posts = postService.findAll().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt))
                .collect(Collectors.toList());
        return posts.stream()
                .map(postConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("{postId}")
    public PostResponse findById(@PathVariable("postId") Long id) {
        Post post = postService.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return postConverter.convert(post);
    }
}
