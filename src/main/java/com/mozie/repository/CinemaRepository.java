package com.mozie.repository;

import com.mozie.model.database.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, String> {
    Cinema getById(String id);
}
