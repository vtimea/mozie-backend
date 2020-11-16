package com.mozie.controller;

import com.mozie.model.api.login.LoginParameters;
import com.mozie.model.api.login.LoginResponse;
import com.mozie.service.user.AuthToken;
import com.mozie.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginParameters loginParameters) {
        try {
            if (!userService.checkFbTokenValidity(loginParameters.getAccessToken())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            AuthToken authToken = userService.generateToken(loginParameters.getUserId());
            userService.saveUser(loginParameters.getUserId(), authToken);
            LoginResponse loginResponse = new LoginResponse(authToken.getJwt(), authToken.getExpiresAt());
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}