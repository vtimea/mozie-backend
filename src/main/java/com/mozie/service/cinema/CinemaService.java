package com.mozie.service.cinema;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;
import com.mozie.model.database.Seat;

import java.time.LocalDateTime;
import java.util.List;

public interface CinemaService {
    List<Cinema> getAllCinemas();

    List<Screening> getScreeningsByCinema(String id);

    List<Screening> getScreeningsByCinemaAndDate(String id, LocalDateTime date);

    List<Screening> getScreeningsByMovie(String movieId);

    List<Seat> getSeatsByScreening(int id);
}
