package com.mozie;

import com.mozie.model.database.Cinema;
import com.mozie.repository.CinemaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CinemaRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CinemaRepository cinemaRepository;

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
    }

    @Test
    public void whenFindById_thenReturnCinema() {
        Cinema cinema = cinemaRepository.findById("1").get();
        assertThat(cinema.getAddress()).isEqualTo("Address 1");
    }
}
