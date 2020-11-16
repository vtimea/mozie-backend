package com.mozie.service.user;

import com.mozie.model.database.User;

public interface UserService {
    boolean checkFbTokenValidity(String inputToken);

    AuthToken generateToken(String userId);

    User getUser(String userId);

    void saveUser(String userId, AuthToken authToken);
}
