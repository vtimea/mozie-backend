package com.mozie.service.movie;

import com.mozie.model.database.Movie;
import com.mozie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> getSoonMovies() {
        return movieRepository.findByStatus(Movie.Status.UNRELEASED);
    }

    @Override
    public List<Movie> getNowPlayingMovies() {
        return movieRepository.findByStatus(Movie.Status.RELEASED);
    }

    @Override
    public Movie getMovieById(String id) {
        return movieRepository.findMovieById(id);
    }
}
