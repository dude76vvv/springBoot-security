package com.example.authDemo.services;

import com.example.authDemo.dtos.AdminResponse;
import com.example.authDemo.dtos.LoginDTO;
import com.example.authDemo.dtos.RegisterDTO;
import com.example.authDemo.models.Role;
import com.example.authDemo.models.UserEntity;
import com.example.authDemo.repository.RoleRepository;
import com.example.authDemo.repository.UserRepository;
import com.example.authDemo.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    //register true/false to return string message if successful
    public boolean register(RegisterDTO req){

        Boolean res  =false;

        //check if user exist
        if (!userRepo.existsByEmail(req.getEmail())){


//            var newUser = UserEntity.builder()
//                    .email(req.getEmail())
//                    .password(passwordEncoder.encode(req.getPassword()))
//                    .build();

            //use empty constructor to create instance of user
            var newUser = new UserEntity();
            var _email = req.getEmail();
            var _pswd = passwordEncoder.encode(req.getPassword());

            Role _role= roleRepo.findByName("USER").get();

            newUser.setEmail(_email);
            newUser.setPassword(_pswd);
            newUser.addRole(_role);
            userRepo.save(newUser);
            res= true;
        }


        return res;

    }


    public String login(LoginDTO req){

        //use auth manager to check if such user exist in db
        //if exist, generate the jwt token and return

        String token=null;

        try {
//          will throw if fail
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken (req.getEmail(), req.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JWTUtil.generateToken(authentication);

        }catch(Exception e){

            System.out.println(e);
        }


        return token;

    }


    public AdminResponse add_adminRole(String email, int id) {

        UserEntity newAdmin= null;

        var existingUser = userRepo.findByEmailAndId(email,id);
        if (existingUser.isPresent()){

          newAdmin = existingUser.get();

          Role _role= roleRepo.findByName("ADMIN").get();

          newAdmin.addRole(_role);

//        need to save to update database
          userRepo.save(newAdmin);

          if (newAdmin!= null)

              return mapToDto(newAdmin);

        }

        return null;


    }

    //mapper from user To admin Response

    public AdminResponse mapToDto(UserEntity user){

        //convert from set of roles object to list of string
        //Role.toString return only roleName

        List<String> lis = user.getRoles().stream().map(
                x -> x.toString()
        ).collect(Collectors.toList());

        //traditional approach
        AdminResponse adminResObj = new AdminResponse();
        adminResObj.setEmail(user.getEmail());
        adminResObj.setListOfRoles(lis);

        //using builder approach
        AdminResponse adminResObj2 = AdminResponse.builder()
                .email(user.getEmail())
                .listOfRoles(lis)
                .build();

        return adminResObj2;

    }

}

