package com.mozie.service.movie;

import com.mozie.model.database.Movie;
import com.mozie.repository.MovieRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRespository movieRespository;

    @Override
    public List<Movie> getSoonMovies() {
        return movieRespository.findByStatus(Movie.Status.UNRELEASED);
    }

    @Override
    public List<Movie> getNowPlayingMovies() {
        return movieRespository.findByStatus(Movie.Status.RELEASED);
    }

    @Override
    public Movie getMovieById(String id) {
        return movieRespository.findMovieById(id);
    }
}
