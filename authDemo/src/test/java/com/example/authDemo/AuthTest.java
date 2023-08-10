package com.example.authDemo;


import com.example.authDemo.models.Role;
import com.example.authDemo.models.UserEntity;
import com.example.authDemo.repository.RoleRepository;
import com.example.authDemo.repository.UserRepository;
import com.example.authDemo.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AuthTest {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;


    @Test
    public void testCreateUser(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String dummyEmail="threeTest@gmail.com";
        String dummyPassword= passwordEncoder.encode("12345");

        //Return ADMIN
        Role dummyRole_admin= roleRepo.findById(1).get();

        //return USER
        Role dummyRole_user= roleRepo.findById(2).get();

        Set<Role> hs = new HashSet<>();
//        hs.add(dummyRole_user);
        hs.add(dummyRole_admin);

        UserEntity newUser = new UserEntity();
        newUser.setEmail(dummyEmail);
        newUser.setPassword(dummyPassword);
        newUser.setRoles(hs);


        UserEntity savedUser =userRepo.save(newUser);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

}
