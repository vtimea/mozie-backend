package com.mozie.controller;

import com.mozie.model.api.movies.ResponseMovies;
import com.mozie.model.database.Movie;
import com.mozie.model.dto.MovieDetailDto;
import com.mozie.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mozie.model.dto.utils.DtoConverters.convertToFeaturedMovieDto;
import static com.mozie.model.dto.utils.DtoConverters.convertToMovieDetailDto;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping("")
    public ResponseEntity<ResponseMovies> getAllMovies() {
        List<Movie> now = movieService.getNowPlayingMovies();
        List<Movie> soon = movieService.getSoonMovies();
        ResponseMovies responseMovies = new ResponseMovies(convertToFeaturedMovieDto(now), convertToFeaturedMovieDto(soon));
        return new ResponseEntity<>(responseMovies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDto> getMovieDetails(@PathVariable(value = "id") String id) {
        Movie movie = movieService.getMovieById(id);
        MovieDetailDto movieDetailDto = convertToMovieDetailDto(movie);
        return new ResponseEntity<>(movieDetailDto, HttpStatus.OK);
    }
}