package ru.discrimy.yablog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.jpa.repository.PostRepository;

import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    private PostService postService;
    private PostRepository postRepository;

    public IndexController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public ModelAndView index(
            @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> posts = postRepository.findAll(pageable);

        int currentPage = pageable.getPageNumber();
        int minPage = Math.max(currentPage - 3, 0);
        int maxPage = Math.min(currentPage + 3, posts.getTotalPages());

        return new ModelAndView("post/list", Map.of(
                "posts", posts,
                "current", currentPage,
                "min", minPage,
                "max", maxPage
        ));
    }
}
