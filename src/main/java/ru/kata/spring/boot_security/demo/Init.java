package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailServiceimp;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserDetailServiceimp userDetailServiceimp;
    @Autowired
    public Init(UserDetailServiceimp userDetailServiceimp) {
        this.userDetailServiceimp = userDetailServiceimp;
    }

    @PostConstruct
    public void init() {
        Role Adminrole = new Role("ROLE_ADMIN");
        Role Userrole = new Role("ROLE_USER");
        User admin = new User();
        Set<Role> rolesOfAdmin = new HashSet<>();
        Collections.addAll(rolesOfAdmin, Adminrole, Userrole);
        admin.setId(1);
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setRoles(rolesOfAdmin);
        userDetailServiceimp.saveUser(admin);

        User user = new User();
        Set<Role> rolesofUser = new HashSet<>();
        Collections.addAll(rolesofUser, Userrole);
        user.setId(2);
        user.setUsername("user");
        user.setPassword("user");
        user.setFirstName("Simple");
        user.setLastName("User");
        user.setRoles(rolesofUser);
        userDetailServiceimp.saveUser(user);
    }
}
