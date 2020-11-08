package com.mozie.service;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;
import com.mozie.model.database.Seat;
import org.joda.time.DateTime;

import java.util.List;

public interface CinemaService {
    List<Cinema> getAllCinemas();

    List<Screening> getScreeningsByCinema(String id);

    List<Screening> getScreeningsByCinemaAndDate(String id, DateTime date);

    List<Screening> getScreeningsByMovie(String movieId);

    List<Seat> getSeatsByScreening(int id);
}
