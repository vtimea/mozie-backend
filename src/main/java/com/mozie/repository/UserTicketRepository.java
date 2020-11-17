package com.mozie.repository;

import com.mozie.model.database.DbTransaction;
import com.mozie.model.database.User;
import com.mozie.model.database.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {
    UserTicket getById(int id);

    List<UserTicket> getByTransactionId(DbTransaction transactionId);

    List<UserTicket> getByUser(User user);
}
