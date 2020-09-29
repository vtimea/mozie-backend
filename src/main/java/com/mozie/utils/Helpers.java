package com.mozie.utils;

import com.mozie.model.api.login.FbToken;
import com.mozie.model.api.login.FbTokenData;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.mozie.utils.ApiKeys.APPID;
import static com.mozie.utils.ApiKeys.APPSECRET;

public class Helpers {
    public static boolean checkFbTokenValidity(String inputToken) {
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
}