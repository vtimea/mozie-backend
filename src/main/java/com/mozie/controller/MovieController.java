package com.mozie.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mozie.model.api.movies.ResponseMovies;
import com.mozie.model.database.Movie;
import com.mozie.model.dto.ActorDto;
import com.mozie.model.dto.FeaturedMovieDto;
import com.mozie.model.dto.MovieDetailDto;
import com.mozie.service.MovieService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
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
        ResponseMovies responseMovies = new ResponseMovies(convertToFeaturedMovieDto(recommended), convertToFeaturedMovieDto(now), convertToFeaturedMovieDto(soon));
        return new ResponseEntity<>(responseMovies, HttpStatus.OK);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetailDto> getMovieDetails(@PathVariable(value = "id") String id) {
        Movie movie = movieService.getMovieById(id);
        MovieDetailDto movieDetailDto = convertToMovieDetailDto(movie);
        return new ResponseEntity<>(movieDetailDto, HttpStatus.OK);
    }

    private FeaturedMovieDto convertToFeaturedMovieDto(Movie movie) {
        return modelMapper.map(movie, FeaturedMovieDto.class);
    }

    private List<FeaturedMovieDto> convertToFeaturedMovieDto(List<Movie> movies) {
        List<FeaturedMovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movies) {
            movieDtos.add(convertToFeaturedMovieDto(movie));
        }
        return movieDtos;
    }

    private MovieDetailDto convertToMovieDetailDto(Movie movie) {
        MovieDetailDto dto = modelMapper.map(movie, MovieDetailDto.class);
        List<ActorDto> actorDtos = new ArrayList<>();
        Type listType = new TypeToken<List<ActorDto>>() {
        }.getType();
        try {
            actorDtos = new Gson().fromJson(movie.getActors(), listType);
        } catch (JsonSyntaxException ignored) {

        }
        dto.setActors(actorDtos);
        return dto;
    }
}