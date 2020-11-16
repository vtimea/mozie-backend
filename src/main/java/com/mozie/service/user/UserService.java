package com.mozie.service.user;

public interface UserService {
    boolean checkFbTokenValidity(String inputToken);

    AuthToken generateToken(String userId);

    void saveUser(String userId, AuthToken authToken);
}
