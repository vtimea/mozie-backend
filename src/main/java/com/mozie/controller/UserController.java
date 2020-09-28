package com.mozie.controller;

import com.mozie.model.api.login.LoginParameters;
import com.mozie.model.api.login.LoginResponse;
import com.mozie.model.database.User;
import com.mozie.repository.UserRepository;
import com.mozie.utils.AuthToken;
import com.mozie.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/auth")
public class UserController {
    private static final int TOKEN_VALIDITY = (1000 * 60 * 60);

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginParameters loginParameters) {
        try {
            if (!Helpers.checkFbTokenValidity(loginParameters.getAccessToken())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            AuthToken authToken = AuthToken.generateToken(loginParameters.getUserId(), TOKEN_VALIDITY);
            userRepository.save(new User(loginParameters.getUserId(), authToken.getJwt(), authToken.getExpiresAt()));
            LoginResponse loginResponse = new LoginResponse(authToken.getJwt());
            return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}