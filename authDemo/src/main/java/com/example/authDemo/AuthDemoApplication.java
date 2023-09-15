package com.example.authDemo;

import com.example.authDemo.models.Role;
import com.example.authDemo.models.UserEntity;
import com.example.authDemo.repository.RoleRepository;
import com.example.authDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class AuthDemoApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRepository userRepo;


	public static void main(String[] args) {
		SpringApplication.run(AuthDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


//		!!! only run this for the 1st time to setup the roles and admin user !!!


//		//setup possible roles
//		Role admin = new Role();
//		Role user = new Role();
//
//		admin.setId(1);
//		admin.setName("ADMIN");
//
//		user.setId(2);
//		user.setName("USER");
//
//		roleRepo.save(admin);
//		roleRepo.save(user);
//
//		Role admin_role= roleRepo.findById(1).get();
//
//		Set<Role> hs = new HashSet<>();
//		hs.add(admin_role);
//
//		//setup 1st admin user
//
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String admin1Email="admin1@gmail.com";
//		String admin1Password= passwordEncoder.encode("12345");
//
//		UserEntity admin1 = new UserEntity();
//		admin1.setEmail(admin1Email);
//		admin1.setPassword(admin1Password);
//		admin1.setRoles(hs);
//
//		userRepo.save(admin1);


	}
}
