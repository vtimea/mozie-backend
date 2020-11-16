package com.mozie.repository;

import com.mozie.model.database.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {
    UserTicket getById(int id);

    List<UserTicket> getByTransactionId(int transactionId);
}
