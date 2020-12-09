package com.mozie.controller;

import com.mozie.model.api.tickets.PaymentResult;
import com.mozie.model.api.tickets.ResponseClientToken;
import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.DbTransaction;
import com.mozie.model.database.TicketType;
import com.mozie.model.dto.TicketTypeDto;
import com.mozie.model.dto.utils.DtoConverters;
import com.mozie.service.ticket.TicketService;
import com.mozie.utils.ErrorResponse;
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
    public ResponseEntity<List<TicketTypeDto>> getTicketsByPrice(@PathParam(value = "type") String type) {
        List<TicketType> ticketTypes;
        if (type == null || type.isEmpty()) {
            ticketTypes = ticketService.getAllTicketTypes();
        } else {
            ticketTypes = ticketService.getTicketTypeByType(type);
        }
        List<TicketTypeDto> ticketTypeDtos = DtoConverters.convertToTicketDtoList(ticketTypes);
        return new ResponseEntity<>(ticketTypeDtos, HttpStatus.OK);
    }

    @PostMapping("payment")
    public ResponseEntity generateClientToken(@RequestHeader("Authorization") String userToken, @RequestBody TicketOrder ticketOrder) {
        DbTransaction dbTransaction;
        try {
            dbTransaction = ticketService.createTransaction(userToken, ticketOrder);
        } catch (ErrorResponse e) {
            return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        String clientToken = ticketService.createClientToken();
        ResponseClientToken response = new ResponseClientToken();
        response.setTransactionId(dbTransaction.getId());
        response.setClientToken(clientToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("payment/nonce")
    public ResponseEntity<Boolean> receivePaymentNonce(@RequestBody PaymentResult paymentResult) {
        boolean isSuccessful = ticketService.doTransaction(paymentResult.getNonce(), paymentResult.getTransactionId());
        return new ResponseEntity<>(isSuccessful, HttpStatus.OK);
    }
}
