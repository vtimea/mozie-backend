package com.mozie.service;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;

import java.util.List;

public interface CinemaService {
    List<Cinema> getAllCinemas();

    List<Screening> getScreeningsByCinema(String id);
}
