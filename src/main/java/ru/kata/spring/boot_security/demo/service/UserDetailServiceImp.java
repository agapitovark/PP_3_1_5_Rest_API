package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepo;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserDetailServiceImp implements UserDetailService {
    private UserRepo userRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


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


    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(new User());
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        userRepo.saveAndFlush(user);
    }

    public User getUserByUsername(String username) {

        return userRepo.getUserByUsername(username);
    }

    @Transactional
    public void changeUser(int id, User userToUpdate) {
        User userById = userRepo.findById(id).orElse(new User());
        userById.setId(userToUpdate.getId());
        userById.setFirstName(userToUpdate.getFirstName());
        userById.setLastName(userToUpdate.getLastName());
        userById.setUsername(userToUpdate.getUsername());
        userById.setPassword(userToUpdate.getPassword());
        userById.setRoles(userToUpdate.getRoles());
        userRepo.save(userById);
    }
}
