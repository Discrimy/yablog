package ru.discrimy.yablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.service.PostService;

import java.util.Collections;

@Controller
@RequestMapping("/")
public class IndexController {
    private PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("post/list", Collections.singletonMap("posts", postService.findAll()));
    }
}
