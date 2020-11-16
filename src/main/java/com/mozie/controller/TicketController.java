package com.mozie.controller;

import com.mozie.model.api.tickets.PaymentResult;
import com.mozie.model.api.tickets.ResponseClientToken;
import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.DbTransaction;
import com.mozie.model.database.TicketType;
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
        List<TicketType> ticketTypes;
        if (type == null || type.isEmpty()) {
            ticketTypes = ticketService.getAllTicketTypes();
        } else {
            ticketTypes = ticketService.getTicketTypeByType(type);
        }
        List<TicketDto> ticketDtos = DtoConverters.convertToTicketDtoList(ticketTypes);
        return new ResponseEntity<>(ticketDtos, HttpStatus.OK);
    }

    @PostMapping("payments/ticket")
    public ResponseEntity<ResponseClientToken> generateClientToken(@RequestBody TicketOrder ticketOrder) {
        DbTransaction dbTransaction = ticketService.createTransaction(ticketOrder);
        String clientToken = ticketService.createClientToken();
        ResponseClientToken response = new ResponseClientToken();
        response.setTransactionId(dbTransaction.getId());
        response.setClientToken(clientToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("payments/ticket/nonce")
    public ResponseEntity<Boolean> receivePaymentNonce(@RequestBody PaymentResult paymentResult) {
        boolean isSuccessful = ticketService.doTransaction(paymentResult.getNonce(), paymentResult.getTransactionId());
        //todo
        return new ResponseEntity<>(isSuccessful, HttpStatus.OK);
    }
}
