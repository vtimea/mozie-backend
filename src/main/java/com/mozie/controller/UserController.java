package com.mozie.controller;

import com.mozie.model.dto.UserTicketDto;
import com.mozie.service.user.UserService;
import com.mozie.utils.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("tickets")
    public ResponseEntity getUserTickets(@RequestHeader("Authorization") String userToken) {
        Map<Integer, UserTicketDto> userTickets;
        try {
            userTickets = userService.getUserTicketDtos(userToken);
        } catch (ErrorResponse e) {
            return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userTickets, HttpStatus.OK);
    }
}
