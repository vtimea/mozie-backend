package com.mozie.controller;

import com.mozie.model.User;
import com.mozie.model.login.LoginParameters;
import com.mozie.model.login.LoginResponse;
import com.mozie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginParameters loginParameters) {
        try {
            if (!Helpers.checkTokenValidity(loginParameters.getAccessToken())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            String accessToken = Helpers.generateAccessToken();
            userRepository.save(new User(loginParameters.getUserId())); // todo access token?
            LoginResponse loginResponse = new LoginResponse(accessToken);
            return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}