package com.mozie.model.api.movies;

import com.mozie.model.dto.FeaturedMovieDto;

import java.util.ArrayList;
import java.util.List;


public class ResponseMovies {
    private List<FeaturedMovieDto> released = new ArrayList<>();
    private List<FeaturedMovieDto> unreleased = new ArrayList<>();

    public ResponseMovies() {
    }

    public ResponseMovies(List<FeaturedMovieDto> released, List<FeaturedMovieDto> unreleased) {
        this.released = released;
        this.unreleased = unreleased;
    }

    public List<FeaturedMovieDto> getReleased() {
        return released;
    }

    public void setReleased(List<FeaturedMovieDto> released) {
        this.released = released;
    }

    public List<FeaturedMovieDto> getUnreleased() {
        return unreleased;
    }

    public void setUnreleased(List<FeaturedMovieDto> unreleased) {
        this.unreleased = unreleased;
    }
}
