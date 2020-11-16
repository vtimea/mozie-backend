package com.mozie.service.ticket;

import com.braintreegateway.*;
import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.*;
import com.mozie.repository.*;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.mozie.utils.ApiKeys.*;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    SeatsRepository seatsRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserTicketRepository userTicketRepository;

    private static final BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            BRAINTREE_MERCHANT_ID,
            BRAINTREE_PUBLIC_KEY,
            BRAINTREE_PRIVATE_KEY
    );

    @Override
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    @Override
    public List<TicketType> getTicketTypeByType(String type) {
        return ticketTypeRepository.getAllByType(type);
    }

    @Override
    public TicketType getTicketTypeById(int id) {
        return ticketTypeRepository.getById(id);
    }

    @Override
    public DbTransaction createTransaction(TicketOrder ticketOrder) {
        DbTransaction transaction = new DbTransaction();
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
        transaction.setStatus(DbTransaction.Status.CREATED);
        LocalDateTime currentTime = LocalDateTime.now();
        transaction.setCreatedAt(currentTime);
        transaction.setUpdatedAt(currentTime);

        List<Integer> ticketTypes = ticketOrder.getTicketTypes();
        List<Integer> seats = ticketOrder.getSeats();
        if (ticketTypes.size() != seats.size()) {
            // todo error invalid tickets types and seats
        }
        transaction = transactionRepository.saveAndFlush(transaction);
        createTickets(ticketOrder.getUserId(), ticketTypes, seats, transaction);
        return transaction;
    }

    @Override
    public String createClientToken() {
        return gateway.clientToken().generate(new ClientTokenRequest());
    }

    @Override
    public boolean doTransaction(String nonce, int transactionId) {
        DbTransaction transaction = transactionRepository.getById(transactionId);
        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal(transaction.getAmount()))
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();
        Result<Transaction> result = gateway.transaction().sale(request);
        LocalDateTime purchaseDate = LocalDateTime.now();
        transaction.setSuccessful(result.isSuccess());
        transaction.setStatus(DbTransaction.Status.COMPLETED);
        transaction.setUpdatedAt(purchaseDate);
        if (result.isSuccess()) {
            setTicketPurchaseDate(transactionId, purchaseDate);
            return true;
        }
        deleteTickets(transactionId);
        return false;
    }

    private boolean checkSumAmount(List<Integer> ticketTypes, int sumAmount) {
        int calculatedAmount = 0;
        for (Integer ticketId : ticketTypes) {
            TicketType ticketType = getTicketTypeById(ticketId);
            if (ticketType == null) {
                // todo throw invalid ticket type error
                return false;
            }
            calculatedAmount += ticketType.getPrice();
        }
        return calculatedAmount == sumAmount;
    }

    private void createTickets(String userId, List<Integer> ticketTypes, List<Integer> seats, DbTransaction dbTransaction) {
        for (int ticketTypeId : ticketTypes) {
            for (int seatId : seats) {
                UserTicket userTicket = new UserTicket();

                // Reserve seats
                Seat seat = seatsRepository.findById(seatId);
                if (!seat.getAvailable()) {
                    // todo error
                }
                seat.setAvailable(false);
                seat = seatsRepository.save(seat);
                userTicket.setSeat(seat);   // todo error

                //  Set ticket type
                TicketType ticketType = ticketTypeRepository.getById(ticketTypeId);
                userTicket.setTicketType(ticketType);   // todo error

                //  Set user and transaction
                User user = userRepository.findUserByUserId(userId);
                userTicket.setUser(user);   // todo error
                userTicket.setTransaction(dbTransaction);

                userTicketRepository.save(userTicket);
            }
        }
    }

    private void setTicketPurchaseDate(int transactionId, LocalDateTime purchaseDate) {
        DbTransaction transaction = transactionRepository.getById(transactionId);
        List<UserTicket> userTickets = userTicketRepository.getByTransactionId(transaction);
        for (UserTicket userTicket : userTickets) {
            userTicket.setPurchasedOn(purchaseDate);
        }
        userTicketRepository.saveAll(userTickets);
    }

    private void deleteTickets(int transactionId) {
        DbTransaction transaction = transactionRepository.getById(transactionId);
        List<UserTicket> userTickets = userTicketRepository.getByTransactionId(transaction);
        for (UserTicket userTicket : userTickets) {
            Seat seat = userTicket.getSeat();
            seat.setAvailable(true);
            seatsRepository.save(seat);
        }
        userTicketRepository.deleteAll(userTickets);
    }
}
