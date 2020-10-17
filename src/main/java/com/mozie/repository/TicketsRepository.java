package com.mozie.repository;

import com.mozie.model.database.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketsRepository extends JpaRepository<Ticket, Integer> {
    Ticket getById(int id);

    List<Ticket> getAllByType(String type);
}
