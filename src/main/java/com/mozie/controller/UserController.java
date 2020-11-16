package com.mozie.controller;

import com.mozie.model.api.login.LoginParameters;
import com.mozie.model.api.login.LoginResponse;
import com.mozie.service.user.AuthToken;
import com.mozie.service.user.UserService;
import com.mozie.utils.ErrorMessages;
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
    public ResponseEntity login(@RequestBody LoginParameters loginParameters) {
        try {
            if (!userService.checkFbTokenValidity(loginParameters.getAccessToken())) {
                return new ResponseEntity<>(ErrorMessages.ERROR_INVALID_FB_TOKEN, HttpStatus.BAD_REQUEST);
            }
            AuthToken authToken = userService.generateToken(loginParameters.getUserId());
            userService.saveUser(loginParameters.getUserId(), authToken);
            LoginResponse loginResponse = new LoginResponse(authToken.getJwt(), authToken.getExpiresAt());
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorMessages.ERROR_CANNOT_CREATE_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}