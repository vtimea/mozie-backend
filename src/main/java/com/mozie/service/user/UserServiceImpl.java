package com.mozie.service.user;

import com.mozie.model.api.login.FbToken;
import com.mozie.model.api.login.FbTokenData;
import com.mozie.model.database.*;
import com.mozie.model.dto.TicketInfoDto;
import com.mozie.model.dto.UserTicketDto;
import com.mozie.repository.UserRepository;
import com.mozie.repository.UserTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mozie.utils.ApiKeys.APPID;
import static com.mozie.utils.ApiKeys.APPSECRET;
import static com.mozie.utils.ErrorResponses.NO_SUCH_USER;

@Service
public class UserServiceImpl implements UserService {
    private static final int SCREENING_START_LIMIT = 30;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTicketRepository userTicketRepository;

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
        return AuthToken.generateToken(userId);
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public void saveUser(String userId, AuthToken authToken) {
        userRepository.save(new User(userId, authToken.getJwt(), authToken.getExpiresAt()));
    }

    @Override
    public Map<Integer, UserTicketDto> getUserTicketDtos(String userToken) {
        List<UserTicket> userTickets = getUserTickets(userToken);
        Map<Integer, UserTicketDto> result = convertToUserTicketDto(userTickets);
        return result;
    }

    private List<UserTicket> getUserTickets(String userToken) {
        User user = userRepository.findUserByToken(userToken);
        if (user == null) {
            throw NO_SUCH_USER(userToken);
        }
        List<UserTicket> userTickets = userTicketRepository.getByUser(user);
        userTickets.removeIf(userTicket -> {
            Seat seat = userTicket.getSeat();
            Screening screening = seat.getScreening();
            return screening.getStartTime().toDateTime().plusMinutes(SCREENING_START_LIMIT).isBeforeNow();
        });
        return userTickets;
    }

    private Map<Integer, UserTicketDto> convertToUserTicketDto(List<UserTicket> userTickets) {
        Map<Integer, UserTicketDto> result = new HashMap<>();
        Map<Integer, List<UserTicket>> ticketsGrouped = userTickets.stream().collect(Collectors.groupingBy(ticket -> ticket.getSeat().getScreening().getId()));
        for (Integer key : ticketsGrouped.keySet()) {
            List<UserTicket> tickets = ticketsGrouped.get(key);
            if (tickets.isEmpty()) {
                continue;
            }
            Seat seat = tickets.get(0).getSeat();
            Screening screening = seat.getScreening();
            Movie movie = screening.getMovie();

            UserTicketDto dto = new UserTicketDto();
            dto.setMovieTitle(movie.getTitle());
            dto.setMovieStartTime(screening.getStartTime().toString());
            dto.setMoviePosterUrl(movie.getPosterUrl());
            dto.setScreeningType(screening.getType());
            dto.setCinemaName(screening.getCinema().getName());

            List<TicketInfoDto> infoDtos = new ArrayList<>();
            for (UserTicket ticket : tickets) {
                TicketInfoDto ticketInfoDto = new TicketInfoDto();
                ticketInfoDto.setTicketId(ticket.getId());
                ticketInfoDto.setType(ticket.getTicketType().getName());
                ticketInfoDto.setPrice(ticket.getTicketType().getPrice());
                ticketInfoDto.setCol(ticket.getSeat().getCol());
                ticketInfoDto.setRow(ticket.getSeat().getRow());
                infoDtos.add(ticketInfoDto);
            }
            dto.setTickets(infoDtos);
            result.put(key, dto);
        }
        return result;
    }
}
