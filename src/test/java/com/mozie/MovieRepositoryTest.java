package com.mozie;

import com.mozie.model.database.Movie;
import com.mozie.repository.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MovieRepository movieRepository;

    @Before
    public void setUp() {
        Movie movie1 = new Movie();
        movie1.setId("1");
        movie1.setTitle("Movie 1");
        movie1.setStatus(Movie.Status.RELEASED);
        testEntityManager.persist(movie1);

        Movie movie2 = new Movie();
        movie2.setId("2");
        movie2.setTitle("Movie 2");
        movie2.setStatus(Movie.Status.RELEASED);
        testEntityManager.persist(movie2);

        Movie movie3 = new Movie();
        movie3.setId("3");
        movie3.setTitle("Movie 3");
        movie3.setStatus(Movie.Status.UNRELEASED);
        testEntityManager.persist(movie3);
    }

    @Test
    public void whenFindById_thenReturnMovie() {
        Movie movie = movieRepository.findById("1").get();
        assertThat(movie.getTitle()).isEqualTo("Movie 1");
    }

    @Test
    public void whenFindByStatus_thenReturnMovie() {
        List<Movie> result = movieRepository.findByStatus(Movie.Status.UNRELEASED);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Movie 3");
    }
}