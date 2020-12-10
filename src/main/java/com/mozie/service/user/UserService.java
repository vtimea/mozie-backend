package com.mozie.service.user;

import com.mozie.model.dto.UserTicketDto;

import java.util.Map;

public interface UserService {
    boolean checkFbTokenValidity(String inputToken);

    AuthToken generateToken(String userId);

    void saveUser(String userId, AuthToken authToken);

    Map<Integer, UserTicketDto> getUserTicketDtos(String userToken);
}
