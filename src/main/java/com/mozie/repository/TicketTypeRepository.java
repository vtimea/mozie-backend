package com.mozie.repository;

import com.mozie.model.database.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {
    TicketType getById(int id);

    List<TicketType> getAllByType(String type);
}
