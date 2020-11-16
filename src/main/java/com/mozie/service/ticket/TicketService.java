package com.mozie.service.ticket;

import com.mozie.model.api.tickets.TicketOrder;
import com.mozie.model.database.Ticket;
import com.mozie.model.database.Transaction;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTicketTypes();

    List<Ticket> getTicketTypeByType(String type);

    Ticket getTicketTypeById(int id);

    Transaction createTransaction(TicketOrder ticketOrder);

    Transaction saveTransaction(Transaction transaction);

    String createClientToken();
}
