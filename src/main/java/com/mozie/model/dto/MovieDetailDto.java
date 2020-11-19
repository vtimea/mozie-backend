package com.mozie.model.dto;

import com.mozie.model.database.Movie;

import java.util.List;

public class MovieDetailDto {
    private String id;
    private String title;
    private String genre;
    private int length;
    private String description;
    private String posterUrl;
    private List<ActorDto> actorsList;
    private Movie.Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<ActorDto> getActors() {
        return actorsList;
    }

    public void setActors(List<ActorDto> actors) {
        this.actorsList = actors;
    }

    public Movie.Status getStatus() {
        return status;
    }

    public void setStatus(Movie.Status status) {
        this.status = status;
    }
}
