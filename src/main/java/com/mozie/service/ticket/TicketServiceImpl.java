package com.mozie.service.ticket;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.Ticket;
import com.mozie.model.database.Transaction;
import com.mozie.model.database.User;
import com.mozie.repository.TicketsRepository;
import com.mozie.repository.TransactionRepository;
import com.mozie.repository.UserRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mozie.utils.ApiKeys.*;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketsRepository ticketsRepository;

    @Autowired
    TransactionRepository transactionRepository;

    private static final BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            BRAINTREE_MERCHANT_ID,
            BRAINTREE_PUBLIC_KEY,
            BRAINTREE_PRIVATE_KEY
    );

    @Override
    public List<Ticket> getAllTicketTypes() {
        return ticketsRepository.findAll();
    }

    @Override
    public List<Ticket> getTicketTypeByType(String type) {
        return ticketsRepository.getAllByType(type);
    }

    @Override
    public Ticket getTicketTypeById(int id) {
        return ticketsRepository.getById(id);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction createTransaction(TicketOrder ticketOrder) {
        Transaction transaction = new Transaction();
        User user = userRepository.findUserByUserId(ticketOrder.getUserId());   // todo user not found error
        if (user == null) {
            return null; //todo error no such user
        }
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
            Ticket ticketType = getTicketTypeById(ticketId);
            if (ticketType == null) {
                // todo throw invalid ticket type error
                return false;
            }
            calculatedAmount += ticketType.getPrice();
        }
        return calculatedAmount == sumAmount;
    }

    @Override
    public String createClientToken() {
        return gateway.clientToken().generate(new ClientTokenRequest());
    }
}
