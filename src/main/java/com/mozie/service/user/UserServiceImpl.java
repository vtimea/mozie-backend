package com.mozie.service.user;

import com.mozie.model.api.login.FbToken;
import com.mozie.model.api.login.FbTokenData;
import com.mozie.model.database.User;
import com.mozie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.mozie.utils.ApiKeys.APPID;
import static com.mozie.utils.ApiKeys.APPSECRET;

@Service
public class UserServiceImpl implements UserService {
    private static final int TOKEN_VALIDITY = (1000 * 60 * 60);

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkFbTokenValidity(String inputToken) {
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

    @Override
    public AuthToken generateToken(String userId) {
        return AuthToken.generateToken(userId, TOKEN_VALIDITY);
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public void saveUser(String userId, AuthToken authToken) {
        userRepository.save(new User(userId, authToken.getJwt(), authToken.getExpiresAt()));
    }
}
