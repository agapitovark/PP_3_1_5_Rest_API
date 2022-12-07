package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceimp;

@Controller
public class AdminController {
    private final UserDetailServiceimp userDetailServiceimp;

    @Autowired
    public AdminController(UserDetailServiceimp userDetailServiceimp) {
        this.userDetailServiceimp = userDetailServiceimp;
    }
    @GetMapping("/admin")
    public String getAllUsers(Model model){
        model.addAttribute("users", userDetailServiceimp.findAll());
        return "admin/allusers";
    }
    @GetMapping("/admin/{id}")
    public String getUser(@PathVariable ("id") int id, Model model) {
        model.addAttribute("user", userDetailServiceimp.getUserById(id));
        return "admin/user";
    }
    @GetMapping("/admin/newuser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("admin/newuser")
    public String saveUser(@ModelAttribute("user") User user) {
        userDetailServiceimp.saveUser(user);
        return "redirect:/admin";
    }
    @RequestMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userDetailServiceimp.deleteUser(id);
        return "redirect:/admin";
    }
    @GetMapping("admin/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userDetailServiceimp.getUserById(id));
        return "admin/edit";
    }
    @PostMapping("admin/edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id){
        userDetailServiceimp.changeUser(id,user);
        return "redirect:/admin";
    }


}
