package com.mozie.service.ticket;

import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.DbTransaction;
import com.mozie.model.database.TicketType;

import java.util.List;

public interface TicketService {
    List<TicketType> getAllTicketTypes();

    List<TicketType> getTicketTypeByType(String type);

    TicketType getTicketTypeById(int id);

    DbTransaction createTransaction(String userToken, TicketOrder ticketOrder);

    String createClientToken();

    boolean doTransaction(String nonce, int transactionId);
}
