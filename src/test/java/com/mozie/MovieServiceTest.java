package com.mozie;

import com.mozie.model.database.Movie;
import com.mozie.repository.MovieRepository;
import com.mozie.service.movie.MovieServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void whenGetSoonMovies_thenReturnMoviesList() {
        Movie movie1 = new Movie();
        movie1.setId("1");
        movie1.setTitle("Movie 1");
        movie1.setStatus(Movie.Status.UNRELEASED);

        Movie movie2 = new Movie();
        movie2.setId("2");
        movie2.setTitle("Movie 2");
        movie2.setStatus(Movie.Status.UNRELEASED);
        List<Movie> expectedMovies = Arrays.asList(movie1, movie2);
        doReturn(expectedMovies).when(movieRepository).findByStatus(Movie.Status.UNRELEASED);

        List<Movie> resultMovies = movieService.getSoonMovies();
        assertThat(resultMovies).isEqualTo(expectedMovies);
    }

    @Test
    public void whenGetNowMovies_thenReturnMoviesList() {
        Movie movie1 = new Movie();
        movie1.setId("1");
        movie1.setTitle("Movie 1");
        movie1.setStatus(Movie.Status.RELEASED);

        Movie movie2 = new Movie();
        movie2.setId("2");
        movie2.setTitle("Movie 2");
        movie2.setStatus(Movie.Status.RELEASED);
        List<Movie> expectedMovies = Arrays.asList(movie1, movie2);
        doReturn(expectedMovies).when(movieRepository).findByStatus(Movie.Status.RELEASED);

        List<Movie> resultMovies = movieService.getNowPlayingMovies();
        assertThat(resultMovies).isEqualTo(expectedMovies);
    }

    @Test
    public void whenGetMovieById_thenReturnMovie() {
        Movie expectedMovie = new Movie();
        expectedMovie.setId("1");
        expectedMovie.setTitle("Movie 1");
        expectedMovie.setStatus(Movie.Status.RELEASED);
        doReturn(expectedMovie).when(movieRepository).findMovieById("1");

        Movie resultMovie = movieService.getMovieById("1");
        assertThat(resultMovie).isEqualTo(expectedMovie);
    }
}
