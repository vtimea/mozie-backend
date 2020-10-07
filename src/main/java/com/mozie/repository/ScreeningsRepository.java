package com.mozie.repository;

import com.mozie.model.database.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningsRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findScreeningsByCinema_Id(String id);
}
