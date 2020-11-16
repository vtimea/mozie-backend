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
        } else {
            tickets = ticketService.getTicketTypeByType(type);
        }
        List<TicketDto> ticketDtos = DtoConverters.convertToTicketDtoList(tickets);
        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }

    @PostMapping("payments/ticket")
    public ResponseEntity<ClientTokenResponse> generateClientToken(@RequestBody TicketOrder ticketOrder) {
        Transaction transaction = ticketService.createTransaction(ticketOrder);
        ticketService.saveTransaction(transaction);
        String clientToken = ticketService.createClientToken();
        ClientTokenResponse response = new ClientTokenResponse();
        response.setClientToken(clientToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
