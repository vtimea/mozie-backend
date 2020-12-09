package com.mozie;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Movie;
import com.mozie.model.database.Screening;
import com.mozie.model.database.Seat;
import com.mozie.repository.ScreeningsRepository;
import com.mozie.repository.SeatsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScreeningsAndSeatsRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ScreeningsRepository screeningsRepository;

    @Autowired
    private SeatsRepository seatsRepository;

    @Before
    public void setUp() {
        Cinema cinema1 = new Cinema();
        cinema1.setId("1");
        cinema1.setAddress("Address 1");
        cinema1.setName("Cinema 1");
        testEntityManager.persist(cinema1);

        Cinema cinema2 = new Cinema();
        cinema2.setId("2");
        cinema2.setAddress("Address 2");
        cinema2.setName("Cinema 2");
        testEntityManager.persist(cinema2);

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

        Screening screening1 = new Screening();
        screening1.setCinema(cinema1);
        screening1.setMovie(movie1);
        screening1.setStartTime(LocalDateTime.of(LocalDate.of(2020, 12, 6), LocalTime.of(12, 0)));
        screening1.setType("2d");
        screening1.setVoice("HU");
        testEntityManager.persist(screening1);

        Screening screening2 = new Screening();
        screening2.setCinema(cinema1);
        screening2.setMovie(movie2);
        screening2.setStartTime(LocalDateTime.of(LocalDate.of(2020, 12, 7), LocalTime.of(13, 0)));
        screening2.setType("2d");
        screening2.setVoice("HU");
        testEntityManager.persist(screening2);

        Screening screening3 = new Screening();
        screening3.setCinema(cinema2);
        screening3.setMovie(movie3);
        screening3.setStartTime(LocalDateTime.of(LocalDate.of(2020, 12, 8), LocalTime.of(14, 0)));
        screening3.setType("2d");
        screening3.setVoice("HU");
        testEntityManager.persist(screening3);

        Seat seat1 = new Seat();
        seat1.setId(1);
        seat1.setCol(1);
        seat1.setRow(1);
        seat1.setRoom(1);
        seat1.setAvailable(true);
        seat1.setScreening(screening1);
        testEntityManager.persist(seat1);

        Seat seat2 = new Seat();
        seat2.setId(2);
        seat2.setCol(1);
        seat2.setRow(1);
        seat2.setRoom(2);
        seat2.setAvailable(true);
        seat2.setScreening(screening2);
        testEntityManager.persist(seat2);

        Seat seat3 = new Seat();
        seat3.setId(3);
        seat3.setCol(1);
        seat3.setRow(1);
        seat3.setRoom(3);
        seat3.setAvailable(true);
        seat3.setScreening(screening3);
        testEntityManager.persist(seat3);
    }

    @Test
    public void whenFindByCinemaId_thenReturnScreening() {
        List<Screening> screenings = screeningsRepository.findScreeningsByCinema_Id("1");
        assertThat(screenings.size()).isEqualTo(2);
        assertThat(screenings.get(0).getCinema().getId()).isEqualTo("1");
        assertThat(screenings.get(1).getCinema().getId()).isEqualTo("1");
    }

    @Test
    public void whenFindByCinemaIdAndStartTimeBetween_thenReturnScreening() {
        List<Screening> screenings = screeningsRepository
                .findScreeningsByCinema_IdAndStartTimeBetween(
                        "1",
                        LocalDateTime.of(LocalDate.of(2020, 12, 6), LocalTime.of(0, 1)),
                        LocalDateTime.of(LocalDate.of(2020, 12, 7), LocalTime.of(23, 59)));
        assertThat(screenings.size()).isEqualTo(2);
        assertThat(screenings.get(0).getCinema().getId()).isEqualTo("1");
        assertThat(screenings.get(1).getCinema().getId()).isEqualTo("1");
    }

    @Test
    public void whenFindByMovieIdAndStartTimeBetween_thenReturnScreening() {
        List<Screening> screenings = screeningsRepository
                .findScreeningsByMovie_IdAndStartTimeBetween(
                        "2",
                        LocalDateTime.of(LocalDate.of(2020, 12, 6), LocalTime.of(0, 1)),
                        LocalDateTime.of(LocalDate.of(2020, 12, 7), LocalTime.of(23, 59)));
        assertThat(screenings.size()).isEqualTo(1);
        assertThat(screenings.get(0).getMovie().getId()).isEqualTo("2");
    }

    @Test
    public void whenFindSeatByScreening_thenReturnSeat() {
        List<Screening> screenings = screeningsRepository.findAll();
        List<Seat> seats = seatsRepository.findSeatsByScreening_Id(screenings.get(0).getId());
        assertThat(seats.size()).isEqualTo(1);
    }

    @Test
    public void whenFindSeatById_thenReturnSeat() {
        Seat seat = seatsRepository.findById(1);
        assertThat(seat.getRoom()).isEqualTo(1);
    }
}