package com.mozie.repository;

import com.mozie.model.database.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRespository extends JpaRepository<Movie, String> {
}
