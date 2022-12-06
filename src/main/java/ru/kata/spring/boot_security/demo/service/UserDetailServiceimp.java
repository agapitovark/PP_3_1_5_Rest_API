package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserDetailServiceimp implements UserDetailsService {
    private UserRepo userRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());

    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Transactional
    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(new User());
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepo.saveAndFlush(user);
    }

    public User getUserByUsername(String username) {

        return userRepo.getUserByUsername(username);
    }
    @Transactional
    public void changeUser(int id, User user) {
        Optional<User> userById = userRepo.findById(id);
        User userFromRepo = userById.get();
        userFromRepo.setId(id);
        userFromRepo.setFirstName(user.getFirstName());
        userFromRepo.setLastName(user.getLastName());
        user.setPassword(user.getPassword());
        userRepo.saveAndFlush(userFromRepo);


    }
}
