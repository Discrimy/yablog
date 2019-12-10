package ru.discrimy.yablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.exceptions.UserNotFoundException;
import ru.discrimy.yablog.service.UserService;

import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}/show")
    public ModelAndView show(@PathVariable("id") Long userId) {
        return new ModelAndView("user/show", Map.of(
                "user", userService.findById(userId)
                        .orElseThrow(UserNotFoundException::new))
        );
    }
}
