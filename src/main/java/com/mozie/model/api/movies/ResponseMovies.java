package com.mozie.model.api.movies;

import com.mozie.model.dto.FeaturedMovieDto;

import java.util.ArrayList;
import java.util.List;


public class ResponseMovies {
    private List<FeaturedMovieDto> now = new ArrayList<>();
    private List<FeaturedMovieDto> soon = new ArrayList<>();

    public ResponseMovies() {
    }

    public ResponseMovies(List<FeaturedMovieDto> now, List<FeaturedMovieDto> soon) {
        this.now = now;
        this.soon = soon;
    }

    public List<FeaturedMovieDto> getNow() {
        return now;
    }

    public void setNow(List<FeaturedMovieDto> now) {
        this.now = now;
    }

    public List<FeaturedMovieDto> getSoon() {
        return soon;
    }

    public void setSoon(List<FeaturedMovieDto> soon) {
        this.soon = soon;
    }
}
