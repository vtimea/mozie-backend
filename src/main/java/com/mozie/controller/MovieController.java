package com.mozie.controller;

import com.mozie.model.api.movies.ResponseMovies;
import com.mozie.model.database.Movie;
import com.mozie.model.dto.FeaturedMovieDto;
import com.mozie.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/movies")
    public ResponseEntity<ResponseMovies> getAllMovies() {
        List<Movie> recommended = movieService.getRecommendedMovies(""); // todo
        List<Movie> now = movieService.getNowPlayingMovies();
        List<Movie> soon = movieService.getSoonMovies();
        ResponseMovies responseMovies = new ResponseMovies(convertToDto(recommended), convertToDto(now), convertToDto(soon));
        return new ResponseEntity<>(responseMovies, HttpStatus.OK);
    }

    private FeaturedMovieDto convertToDto(Movie movie) {
        return modelMapper.map(movie, FeaturedMovieDto.class);
    }

    private List<FeaturedMovieDto> convertToDto(List<Movie> movies) {
        List<FeaturedMovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movies) {
            movieDtos.add(convertToDto(movie));
        }
        return movieDtos;
    }
}