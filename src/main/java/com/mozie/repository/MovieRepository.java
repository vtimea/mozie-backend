package com.mozie.repository;

import com.mozie.model.database.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByStatus(Movie.Status status);

    Movie findMovieById(String id);
}
