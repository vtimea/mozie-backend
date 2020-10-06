package com.mozie.repository;

import com.mozie.model.database.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRespository extends JpaRepository<Movie, String> {
    List<Movie> findMoviesByIsActiveTrue();

    List<Movie> findMoviesByIsActiveFalse();

    Movie findMovieById(String id);
}
