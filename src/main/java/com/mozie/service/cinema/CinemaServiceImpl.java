package com.mozie.service.cinema;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;
import com.mozie.model.database.Seat;
import com.mozie.repository.CinemaRepository;
import com.mozie.repository.ScreeningsRepository;
import com.mozie.repository.SeatsRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Screening> getScreeningsByCinema(String id) {
        return screeningsRepository.findScreeningsByCinema_Id(id);
    }

    @Override
    public List<Screening> getScreeningsByCinemaAndDate(String id, DateTime date) {
        DateTime today = DateTime.now();
        LocalDateTime startDate;
        LocalDateTime endDate;
        if (date.getYear() == today.getYear() && date.getMonthOfYear() == today.getMonthOfYear() && date.getDayOfMonth() == today.getDayOfMonth()) {
            startDate = date.toLocalDateTime().plusMinutes(30);
        } else {
            startDate = date.toLocalDateTime().withMillisOfDay(0);
        }
        endDate = date.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toLocalDateTime();
        return screeningsRepository.findScreeningsByCinema_IdAndStartTimeBetween(id, startDate, endDate);
    }

    @Override
    public List<Screening> getScreeningsByMovie(String movieId) {
        LocalDateTime startDate = DateTime.now().toLocalDateTime().plusMinutes(30);
        return screeningsRepository.findScreeningsByMovie_IdAndStartTimeAfter(movieId, startDate);
    }

    @Override
    public List<Seat> getSeatsByScreening(int id) {
        return seatsRepository.findSeatsByScreening_Id(id);
    }
}
