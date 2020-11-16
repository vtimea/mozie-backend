package com.mozie.controller;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;
import com.mozie.model.database.Seat;
import com.mozie.model.dto.ScheduleDto;
import com.mozie.model.dto.ScreeningDto;
import com.mozie.model.dto.ScreeningRoomDto;
import com.mozie.model.dto.utils.DtoConverters;
import com.mozie.service.cinema.CinemaService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

import static com.mozie.model.dto.utils.DtoConverters.convertToScheduleScreeningDto;
import static com.mozie.model.dto.utils.DtoConverters.convertToScreeningDto;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CinemaController {
    @Autowired
    CinemaService cinemaService;

    @GetMapping("/cinemas")
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleDto>> getScreeningsByCinemaAndDate(@PathParam(value = "cinema") String cinema, @PathParam(value = "date") String date) {
        List<Screening> screenings;
        if (cinema == null || date == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        DateTime dateTime = new DateTime(date);
        screenings = cinemaService.getScreeningsByCinemaAndDate(cinema, dateTime);
        List<ScheduleDto> screeningDtos = convertToScheduleScreeningDto(screenings);
        return new ResponseEntity<>(screeningDtos, HttpStatus.OK);
    }

    @GetMapping("/screenings")
    public ResponseEntity<List<ScreeningDto>> getScreeningsByMovie(@PathParam(value = "movieId") String movieId) {
        if (movieId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Screening> screenings = cinemaService.getScreeningsByMovie(movieId);
        List<ScreeningDto> screeningDtos = convertToScreeningDto(screenings);
        return new ResponseEntity<>(screeningDtos, HttpStatus.OK);
    }

    @GetMapping("/screenings/room")
    public ResponseEntity<ScreeningRoomDto> getSeatsByScreening(@PathParam(value = "screeningId") int screeningId) {
        List<Seat> seats = cinemaService.getSeatsByScreening(screeningId);
        ScreeningRoomDto screeningRoomDto = DtoConverters.convertToScreeningRoomDto(seats);
        return new ResponseEntity<>(screeningRoomDto, HttpStatus.OK);
    }
}