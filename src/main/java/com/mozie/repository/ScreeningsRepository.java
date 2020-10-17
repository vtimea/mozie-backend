package com.mozie.repository;

import com.mozie.model.database.Screening;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningsRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findScreeningsByCinema_Id(String id);

    List<Screening> findScreeningsByCinema_IdAndStartTimeBetween(String cinema_id, LocalDateTime startTime, LocalDateTime startTime2);

    List<Screening> findScreeningsByMovie_Id(String movie_id);
}
