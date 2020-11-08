package com.mozie.repository;

import com.mozie.model.database.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findSeatsByScreening_Id(int id);
}
