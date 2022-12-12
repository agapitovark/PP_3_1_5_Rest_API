package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDetailService userDetailService;

    @Autowired
    public UserController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping
    public String showUser(Principal principal, Model model) {
        User user = userDetailService.getUserByUsername(principal.getName());
        model.addAttribute("user", userDetailService.getUserById(user.getId()));
        return "User";
    }
}
