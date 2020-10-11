package com.mozie.controller;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Screening;
import com.mozie.model.dto.ScreeningDto;
import com.mozie.service.CinemaService;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CinemaController {
    @Autowired
    CinemaService cinemaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/cinemas")
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @GetMapping("/screenings")
    public ResponseEntity<List<ScreeningDto>> getScreenings(@PathParam(value = "cinema") String cinema, @PathParam(value = "date") String date) {
        List<Screening> screenings;
        //todo error checking
        if (date != null) {
            DateTime dateTime = new DateTime(date);
            screenings = cinemaService.getScreeningsByCinemaAndDate(cinema, dateTime);
        } else {
            screenings = cinemaService.getScreeningsByCinema(cinema);
        }
        List<ScreeningDto> screeningDtos = convertToScreeningDtoList(screenings);
        return new ResponseEntity<>(screeningDtos, HttpStatus.OK);
    }

    private ScreeningDto convertToScreeningDto(Screening screening) {
        ScreeningDto dto = modelMapper.map(screening, ScreeningDto.class);
        dto.setCinema_id(screening.getCinema().getId());
        dto.setCinema_name(screening.getCinema().getName());
        dto.setMovie_id(screening.getMovie().getId());
        dto.setMovie_title(screening.getMovie().getTitle());
        dto.setMovie_genre(screening.getMovie().getGenre());
        dto.setMovie_length(screening.getMovie().getLength());
        dto.setMovie_poster(screening.getMovie().getPosterUrl());
        return dto;
    }

    private List<ScreeningDto> convertToScreeningDtoList(List<Screening> screenings) {
        List<ScreeningDto> screeningDtos = new ArrayList<>();
        for (Screening screening : screenings) {
            screeningDtos.add(convertToScreeningDto(screening));
        }
        return screeningDtos;
    }
}