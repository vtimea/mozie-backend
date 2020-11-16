package com.mozie.repository;

import com.mozie.model.database.DbTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<DbTransaction, Integer> {
    DbTransaction getById(int id);
}
