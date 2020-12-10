package com.mozie;

import com.mozie.model.database.Cinema;
import com.mozie.model.database.Seat;
import com.mozie.repository.CinemaRepository;
import com.mozie.repository.SeatsRepository;
import com.mozie.service.cinema.CinemaServiceImpl;
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
public class CinemaServiceTest {
    @Mock
    CinemaRepository cinemaRepository;

    @Mock
    SeatsRepository seatsRepository;

    @InjectMocks
    private CinemaServiceImpl cinemaService;

    @Test
    public void whenGetAllCinemas_thenReturnCinemaList() {
        Cinema cinema1 = new Cinema();
        cinema1.setId("1");
        cinema1.setAddress("Address 1");
        cinema1.setName("Cinema 1");

        Cinema cinema2 = new Cinema();
        cinema2.setId("2");
        cinema2.setAddress("Address 2");
        cinema2.setName("Cinema 2");
        List<Cinema> expectedCinemas = Arrays.asList(cinema1, cinema2);
        doReturn(expectedCinemas).when(cinemaRepository).findAll();

        List<Cinema> resultCinemas = cinemaService.getAllCinemas();
        assertThat(resultCinemas).isEqualTo(expectedCinemas);
    }

    @Test
    public void whenGetSeatsByScreening_thenReturnSeatList() {
        Seat seat = new Seat();
        seat.setId(1);

        List<Seat> expectedSeats = Arrays.asList(seat);
        doReturn(expectedSeats).when(seatsRepository).findSeatsByScreening_Id(1);

        List<Seat> resultSeats = cinemaService.getSeatsByScreening(1);
        assertThat(resultSeats).isEqualTo(expectedSeats);
    }
}
