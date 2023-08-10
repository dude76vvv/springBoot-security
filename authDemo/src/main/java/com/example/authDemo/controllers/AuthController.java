package com.example.authDemo.controllers;


import com.example.authDemo.dtos.LoginDTO;
import com.example.authDemo.dtos.RegisterDTO;
import com.example.authDemo.models.UserEntity;
import com.example.authDemo.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String>  register( @Valid @RequestBody RegisterDTO registerDto) {

            //pass to AuthService to handle
            //check if user exist, throw else add to db

            Boolean isRegistered = authService.register(registerDto);


            if (isRegistered) {

                String msg = String.format("user with email:%s registered successfully", registerDto.getEmail());
                return ResponseEntity.status(200).body(msg);

            }

            return ResponseEntity.status(400).body("Registration failed");

    }

    @PostMapping("/login")
    public ResponseEntity<String> register(@Valid @RequestBody LoginDTO loginDto) {

        //pass to AuthService to handle
        //generate jwt-token and return

        String tokenGenerated =authService.login(loginDto);

        if(tokenGenerated == null)  return ResponseEntity.status(400).body("Not successful to get JWT Token");


        String successMsg = String.format("jwt token: %s",tokenGenerated);

        return ResponseEntity.ok(successMsg);

//        return ResponseEntity.ok(tokenGenerated);

    }




    @GetMapping("/test")
    public String  test() {

        return "hello";

    }




}
