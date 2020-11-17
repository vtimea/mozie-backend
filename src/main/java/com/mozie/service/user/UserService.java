package com.mozie.service.user;

import com.mozie.model.database.User;
import com.mozie.model.dto.UserTicketDto;

import java.util.Map;

public interface UserService {
    boolean checkFbTokenValidity(String inputToken);

    AuthToken generateToken(String userId);

    User getUser(String userId);

    void saveUser(String userId, AuthToken authToken);

    Map<Integer, UserTicketDto> getUserTicketDtos(String userToken);
}
