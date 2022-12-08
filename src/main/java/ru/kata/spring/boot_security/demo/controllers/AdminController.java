package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailService;

@Controller
public class AdminController {
    private final UserDetailService userDetailService;

    @Autowired
    public AdminController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userDetailService.findAll());
        return "admin/allusers";
    }

    @GetMapping("/admin/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDetailService.getUserById(id));
        return "admin/user";
    }

    @GetMapping("/admin/newuser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("admin/newuser")
    public String saveUser(@ModelAttribute("user") User user) {
        userDetailService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userDetailService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDetailService.getUserById(id));
        return "admin/edit";
    }

    @PatchMapping("admin/{id}/edit")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDetailService.changeUser(id, user);
        return "redirect:/admin";
    }


}
