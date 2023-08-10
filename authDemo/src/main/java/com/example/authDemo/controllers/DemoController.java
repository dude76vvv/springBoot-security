package com.example.authDemo.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/demo")
@RestController
public class DemoController {


    @GetMapping("/all")
    public ResponseEntity<String> allTest(){

        return ResponseEntity.ok("all is reachable");
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/all2")
    public ResponseEntity<String> allTest2(){

        return ResponseEntity.ok("all is reachable2");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminTest(){

        return ResponseEntity.ok("admin is reachable");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/usr")
    public ResponseEntity<String> useTest(){

        return ResponseEntity.ok("user is reachable");
    }

}
