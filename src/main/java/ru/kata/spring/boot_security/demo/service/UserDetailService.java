package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDetailService extends UserDetailsService {


    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<User> findAll();

    User getUserById(Integer id);

    void deleteUser(Integer id);

    void saveUser(User user);

    User getUserByUsername(String username);

    void changeUser(int id, User user);


}
