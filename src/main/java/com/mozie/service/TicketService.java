package com.mozie.service;

import com.mozie.model.database.Ticket;
import com.mozie.model.database.Transaction;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTicketTypes();

    List<Ticket> getTicketTypeByType(String type);

    Ticket getTicketTypeById(int id);

    Transaction createTransaction(Transaction transaction);
}
