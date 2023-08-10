package com.example.authDemo.controllers;


import com.example.authDemo.dtos.AdminResponse;
import com.example.authDemo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {

    @Autowired
    private AuthService authService;

    //method to add admin role to a particular user -
    //accessible to only admin role verify by jwt token

    //to be configured using  security config instead of annotation

    @PutMapping("/{id}/update_admin")
    public ResponseEntity<?> register(@RequestParam(value = "email", defaultValue = "ur_email", required = true) String email, @PathVariable("id") int id) {

        AdminResponse result = authService.add_adminRole(email,id);

        String success_msg =String.format( "user of %s has admin role now!!!",email);
        String failed_msg =String.format("admin was not successfully added to this user of email: %s",email);

        if (result == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failed_msg);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/test")
    public ResponseEntity<String> allTest(){

        return ResponseEntity.ok("only admin role should reach here!!!");
    }





}
