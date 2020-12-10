package com.mozie.service.cinema;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;
import com.mozie.model.database.Seat;
import com.mozie.repository.CinemaRepository;
import com.mozie.repository.ScreeningsRepository;
import com.mozie.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    ScreeningsRepository screeningsRepository;

    @Autowired
    SeatsRepository seatsRepository;

    @Override
    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    @Override
    public List<Screening> getScreeningsByCinemaAndDate(String id, LocalDateTime date) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate;
        LocalDateTime endDate;
        if (date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDayOfMonth() == today.getDayOfMonth()) {
            startDate = date.plusMinutes(30);
        } else {
            startDate = date.withHour(0).withMinute(0).withSecond(0);
        }
        endDate = date.withHour(23).withMinute(59).withSecond(59);
        return screeningsRepository.findScreeningsByCinema_IdAndStartTimeBetween(id, startDate, endDate);
    }

    @Override
    public List<Screening> getScreeningsByMovie(String movieId) {
        LocalDateTime startDate = LocalDateTime.now().plusMinutes(30);
        LocalDateTime endDate = startDate.plusDays(4).withHour(23).withMinute(59).withSecond(59);
        return screeningsRepository.findScreeningsByMovie_IdAndStartTimeBetween(movieId, startDate, endDate);
    }

    @Override
    public List<Seat> getSeatsByScreening(int id) {
        return seatsRepository.findSeatsByScreening_Id(id);
    }
}
