package ru.discrimy.yablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.service.PostService;

import java.util.Collections;

@Controller
@RequestMapping("post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{id}/show")
    public ModelAndView show(@PathVariable Long id) {
        return new ModelAndView("post/show", Collections.singletonMap(
                "post", postService.findById(id).orElseThrow(RuntimeException::new)));
    }
}
