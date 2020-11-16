package com.mozie.service.movie;

import com.mozie.model.database.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getRecommendedMovies(String userId);

    List<Movie> getSoonMovies();

    List<Movie> getNowPlayingMovies();

    Movie getMovieById(String id);
}
