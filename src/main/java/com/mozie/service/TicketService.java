package com.mozie.service;

import com.mozie.model.database.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getByType(String type);
}
