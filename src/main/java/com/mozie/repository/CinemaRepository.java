package com.mozie.repository;

import com.mozie.model.database.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, String> {
    Cinema getById(String id);
}
