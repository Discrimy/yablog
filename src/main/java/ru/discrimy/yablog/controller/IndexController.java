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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    private PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ModelAndView index(
            @PageableDefault(size = 5, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> posts = postService.findAll(pageable);

        int currentPage = pageable.getPageNumber();
        int minPage = Math.max(currentPage - 3, 0);
        int maxPage = Math.min(currentPage + 3, posts.getTotalPages());

        Map<String, Object> map = new HashMap<>(Map.of(
                "posts", posts,
                "current", currentPage,
                "min", minPage,
                "max", maxPage
        ));
        if (pageable.getPageNumber() == 0) {
            map.put("pinned", postService.findAllPinned());
        }

        return new ModelAndView("post/list", map);
    }
}
