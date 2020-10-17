package com.mozie.service;

import com.mozie.model.database.Ticket;
import com.mozie.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketsRepository ticketsRepository;

    @Override
    public List<Ticket> getByType(String type) {
        return ticketsRepository.getAllByType(type);
    }
}
