package com.mozie.service;

import com.mozie.model.database.Movie;
import com.mozie.repository.MovieRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRespository movieRespository;

    @Override
    public List<Movie> getRecommendedMovies(String userId) {
        return new ArrayList<>();
    }

    @Override
    public List<Movie> getSoonMovies() {
        return movieRespository.findMoviesByIsActiveFalse();
    }

    @Override
    public List<Movie> getNowPlayingMovies() {
        return movieRespository.findMoviesByIsActiveTrue();
    }

    @Override
    public Movie getMovieById(String id) {
        return movieRespository.findMovieById(id);
    }
}
