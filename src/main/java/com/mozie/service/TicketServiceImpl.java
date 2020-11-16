package com.mozie.service;

import com.mozie.model.database.Ticket;
import com.mozie.model.database.Transaction;
import com.mozie.repository.TicketsRepository;
import com.mozie.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketsRepository ticketsRepository;

    @Autowired
    TransactionRepository transactionRepository;

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
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
