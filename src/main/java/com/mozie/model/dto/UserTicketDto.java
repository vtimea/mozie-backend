package com.mozie.model.dto;

import java.util.List;

public class UserTicketDto {
    private String movieTitle;
    private String movieStartTime;
    private String moviePosterUrl;
    private String screeningType;
    private String cinemaName;
    private List<TicketInfoDto> tickets;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieStartTime() {
        return movieStartTime;
    }

    public void setMovieStartTime(String movieStartTime) {
        this.movieStartTime = movieStartTime;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public String getScreeningType() {
        return screeningType;
    }

    public void setScreeningType(String screeningType) {
        this.screeningType = screeningType;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public List<TicketInfoDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketInfoDto> tickets) {
        this.tickets = tickets;
    }
}
