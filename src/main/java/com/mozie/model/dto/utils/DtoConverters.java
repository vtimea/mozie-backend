package com.mozie.model.dto.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mozie.model.database.Movie;
import com.mozie.model.database.Screening;
import com.mozie.model.database.Ticket;
import com.mozie.model.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DtoConverters {
    public static ScheduleDto convertToScheduleScreeningDto(Screening screening) {
        ModelMapper modelMapper = new ModelMapper();
        ScheduleDto dto = modelMapper.map(screening, ScheduleDto.class);
        dto.setCinema_id(screening.getCinema().getId());
        dto.setCinema_name(screening.getCinema().getName());
        dto.setMovie_id(screening.getMovie().getId());
        dto.setMovie_title(screening.getMovie().getTitle());
        dto.setMovie_genre(screening.getMovie().getGenre());
        dto.setMovie_length(screening.getMovie().getLength());
        dto.setMovie_poster(screening.getMovie().getPosterUrl());
        return dto;
    }

    public static List<ScheduleDto> convertToScheduleScreeningDto(List<Screening> screenings) {
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Screening screening : screenings) {
            scheduleDtos.add(convertToScheduleScreeningDto(screening));
        }
        return scheduleDtos;
    }

    public static ScreeningDto convertToScreeningDto(Screening screening) {
        ModelMapper modelMapper = new ModelMapper();
        ScreeningDto dto = modelMapper.map(screening, ScreeningDto.class);
        dto.setCinema_id(screening.getCinema().getId());
        dto.setCinema_name(screening.getCinema().getName());
        dto.setMovie_id(screening.getMovie().getId());
        dto.setMovie_title(screening.getMovie().getTitle());
        return dto;
    }

    public static List<ScreeningDto> convertToScreeningDto(List<Screening> screenings) {
        List<ScreeningDto> scheduleDtos = new ArrayList<>();
        for (Screening screening : screenings) {
            scheduleDtos.add(convertToScreeningDto(screening));
        }
        return scheduleDtos;
    }

    public static FeaturedMovieDto convertToFeaturedMovieDto(Movie movie) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(movie, FeaturedMovieDto.class);
    }

    public static MovieDetailDto convertToMovieDetailDto(Movie movie) {
        ModelMapper modelMapper = new ModelMapper();
        MovieDetailDto dto = modelMapper.map(movie, MovieDetailDto.class);
        List<ActorDto> actorDtos = new ArrayList<>();
        Type listType = new TypeToken<List<ActorDto>>() {
        }.getType();
        try {
            actorDtos = new Gson().fromJson(movie.getActors(), listType);
        } catch (JsonSyntaxException ignored) {

        }
        dto.setActors(actorDtos);
        return dto;
    }

    public static List<FeaturedMovieDto> convertToFeaturedMovieDto(List<Movie> movies) {
        List<FeaturedMovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movies) {
            movieDtos.add(convertToFeaturedMovieDto(movie));
        }
        return movieDtos;
    }

    public static TicketDto convertToTicketDto(Ticket ticket) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ticket, TicketDto.class);
    }

    public static List<TicketDto> convertToTicketDtoList(List<Ticket> tickets) {
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketDtos.add(convertToTicketDto(ticket));
        }
        return ticketDtos;
    }
}
