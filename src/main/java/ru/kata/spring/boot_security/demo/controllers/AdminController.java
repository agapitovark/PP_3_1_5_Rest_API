package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserDetailService userDetailService;
    private final RoleService roleService;

    public AdminController(UserDetailService userDetailService, RoleService roleService) {
        this.userDetailService = userDetailService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("currentUser", userDetailService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userDetailService.findAll());
        model.addAttribute("rolesList", roleService.getRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/save/new")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userDetailService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userDetailService.deleteUser(id);
        return "redirect:/admin";
    }


    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDetailService.changeUser(id, user);
        return "redirect:/admin";
    }


}
