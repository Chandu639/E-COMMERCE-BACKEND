package com.ecommerce.controller.AuthController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.service.UserService.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService service;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity user) {

        service.register(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Registered Successfully");
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity user) {

        String token = service.login(user);

        return ResponseEntity.ok(token);
    }
}