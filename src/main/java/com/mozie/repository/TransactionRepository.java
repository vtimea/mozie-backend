package com.mozie.repository;

import com.mozie.model.database.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction getById(int id);
}
