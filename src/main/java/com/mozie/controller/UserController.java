package com.mozie.controller;

import com.mozie.model.User;
import com.mozie.model.login.FbToken;
import com.mozie.model.login.FbTokenData;
import com.mozie.model.login.LoginParameters;
import com.mozie.model.login.LoginResponse;
import com.mozie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.mozie.controller.ApiKeys.APPID;
import static com.mozie.controller.ApiKeys.APPSECRET;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginParameters loginParameters) {
        try {
            if (!checkTokenValidity(loginParameters.getAccessToken())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            String accessToken = generateAccessToken();
            userRepository.save(new User(loginParameters.getUserId())); // todo access token?
            LoginResponse loginResponse = new LoginResponse(accessToken);
            return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkTokenValidity(String inputToken) {
        String url = "https://graph.facebook.com/debug_token?input_token={inputToken}&access_token={appId}|{appSecret}";
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        ResponseEntity<FbToken> response = restTemplate.getForEntity(url, FbToken.class, inputToken, APPID, APPSECRET);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return false;
        }
        FbTokenData data = response.getBody().getData();
        if (data == null) {
            return false;
        }
        String appId = data.getAppId();
        boolean isValid = data.getValid();
        return appId.equals(APPID) && isValid;
    }

    private String generateAccessToken() {
        return "Generated access token here";
    }
}