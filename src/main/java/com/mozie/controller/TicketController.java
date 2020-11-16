package com.mozie.controller;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.Ticket;
import com.mozie.model.database.Transaction;
import com.mozie.model.database.User;
import com.mozie.model.dto.TicketDto;
import com.mozie.model.dto.utils.DtoConverters;
import com.mozie.repository.UserRepository;
import com.mozie.service.TicketService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

import static com.mozie.utils.ApiKeys.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @Autowired
    UserRepository userRepository;

    private static final BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            BRAINTREE_MERCHANT_ID,
            BRAINTREE_PUBLIC_KEY,
            BRAINTREE_PRIVATE_KEY
    );

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
    public ResponseEntity<Void> generateClientToken(@RequestBody TicketOrder ticketOrder) {
        // 1. Create transaction
        Transaction transaction = createTransaction(ticketOrder);
        ticketService.createTransaction(transaction);
        // 2. Generate client token
        String clientToken = gateway.clientToken().generate(new ClientTokenRequest());
        // 3. Return client token
        return new ResponseEntity<>(null);
    }

    private Transaction createTransaction(TicketOrder ticketOrder) {
        Transaction transaction = new Transaction();
        User user = userRepository.findUserByUserId(ticketOrder.getUserId());   // todo user not found error
        transaction.setUser(user);
        boolean isAmountValid = checkSumAmount(ticketOrder.getTicketTypes(), ticketOrder.getSumAmount());
        if (isAmountValid) {
            transaction.setAmount(ticketOrder.getSumAmount());
        } else {
            // todo invalid amount and ticket types error
        }
        transaction.setSuccessful(false);
        transaction.setStatus(Transaction.Status.CREATED);
        LocalDateTime currentTime = LocalDateTime.now();
        transaction.setCreatedAt(currentTime);
        transaction.setCreatedAt(currentTime);
        return transaction;
    }

    private boolean checkSumAmount(List<Integer> ticketTypes, int sumAmount) {
        int calculatedAmount = 0;
        for (Integer ticketId : ticketTypes) {
            Ticket ticketType = ticketService.getTicketTypeById(ticketId);
            if (ticketType == null) {
                // todo throw invalid ticket type error
                return false;
            }
            calculatedAmount += ticketType.getPrice();
        }
        return calculatedAmount == sumAmount;
    }
}
