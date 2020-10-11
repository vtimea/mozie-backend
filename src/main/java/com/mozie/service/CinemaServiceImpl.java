package com.mozie.service;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;
import com.mozie.repository.CinemaRepository;
import com.mozie.repository.ScreeningsRepository;
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
        LocalDateTime startDate = date.toLocalDateTime().plusMillis(30);
        LocalDateTime endDate = date.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toLocalDateTime();
        return screeningsRepository.findScreeningsByCinema_IdAndStartTimeBetween(id, startDate, endDate);
    }
}
