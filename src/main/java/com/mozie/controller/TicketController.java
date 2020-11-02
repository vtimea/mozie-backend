package com.mozie.controller;

import com.mozie.model.database.Ticket;
import com.mozie.model.dto.TicketDto;
import com.mozie.model.dto.utils.DtoConverters;
import com.mozie.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("")
    public ResponseEntity<List<TicketDto>> getTicketsByPrice(@PathParam(value = "type") String type) {
        List<Ticket> tickets;
        if (type == null || type.isEmpty()) {
            tickets = ticketService.getAll();
        } else if (type.toLowerCase().contains("2d")) {
            tickets = ticketService.getByType("2d");
        } else if (type.toLowerCase().contains("3d")) {
            tickets = ticketService.getByType("3d");
        } else {
            tickets = new ArrayList<>();
        }
        List<TicketDto> ticketDtos = DtoConverters.convertToTicketDtoList(tickets);
        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }
}
