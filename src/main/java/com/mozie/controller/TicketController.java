package com.mozie.controller;

import com.mozie.model.api.tickets.ClientTokenResponse;
import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.Ticket;
import com.mozie.model.database.Transaction;
import com.mozie.model.dto.TicketDto;
import com.mozie.model.dto.utils.DtoConverters;
import com.mozie.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            tickets = ticketService.getAllTicketTypes();
        } else if (type.toLowerCase().contains("2d")) {
            tickets = ticketService.getTicketTypeByType("2d");
        } else if (type.toLowerCase().contains("3d")) {
            tickets = ticketService.getTicketTypeByType("3d");
        } else {
            tickets = new ArrayList<>();
        }
        List<TicketDto> ticketDtos = DtoConverters.convertToTicketDtoList(tickets);
        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }

    @PostMapping("payments/ticket")
    public ResponseEntity<ClientTokenResponse> generateClientToken(@RequestBody TicketOrder ticketOrder) {
        // 1. Create transaction
        Transaction transaction = ticketService.createTransaction(ticketOrder);
        ticketService.saveTransaction(transaction);
        // 2. Generate client token
        String clientToken = ticketService.createClientToken();
        // 3. Return client token
        ClientTokenResponse response = new ClientTokenResponse();
        response.setClientToken(clientToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
