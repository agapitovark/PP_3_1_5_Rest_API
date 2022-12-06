package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceimp;

import java.security.Principal;

@Controller
public class UserController {
    private final UserDetailServiceimp userDetailServiceimp;

    @Autowired
    public UserController(UserDetailServiceimp userDetailServiceimp) {
        this.userDetailServiceimp = userDetailServiceimp;
    }

    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        User user = userDetailServiceimp.getUserByUsername(principal.getName());
        model.addAttribute("user", userDetailServiceimp.getUserById(user.getId()));
        return "user/user";
    }
}
