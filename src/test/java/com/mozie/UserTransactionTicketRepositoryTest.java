package com.mozie;

import com.mozie.model.database.*;
import com.mozie.repository.TransactionRepository;
import com.mozie.repository.UserRepository;
import com.mozie.repository.UserTicketRepository;
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
public class UserTransactionTicketRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Before
    public void setUp() {
        User user1 = new User();
        user1.setUserId("1234");
        user1.setToken("5678");
        user1.setExpires(LocalDateTime.of(LocalDate.of(2020, 12, 10), LocalTime.of(12, 0)));
        testEntityManager.persist(user1);

        User user2 = new User();
        user2.setUserId("abcd");
        user2.setToken("efgh");
        user2.setExpires(LocalDateTime.of(LocalDate.of(2020, 12, 10), LocalTime.of(12, 0)));
        testEntityManager.persist(user2);

        DbTransaction transaction1 = new DbTransaction();
        transaction1.setUser(user1);
        transaction1.setAmount(3000);
        transaction1.setCreatedAt(LocalDateTime.of(LocalDate.of(2020, 12, 10), LocalTime.of(12, 0)));
        transaction1.setUpdatedAt(LocalDateTime.of(LocalDate.of(2020, 12, 10), LocalTime.of(12, 15)));
        transaction1.setStatus(DbTransaction.Status.COMPLETED);
        testEntityManager.persist(transaction1);

        Cinema cinema1 = new Cinema();
        cinema1.setId("1");
        cinema1.setAddress("Address 1");
        cinema1.setName("Cinema 1");
        testEntityManager.persist(cinema1);

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

        TicketType ticketType1 = new TicketType();
        ticketType1.setId(1);
        ticketType1.setName("NORMAL");
        ticketType1.setPrice(1950);
        ticketType1.setType("2d");
        testEntityManager.persist(ticketType1);

        UserTicket ticket2 = new UserTicket();
        ticket2.setUser(user1);
        ticket2.setPurchasedOn(LocalDateTime.of(LocalDate.of(2020, 12, 7), LocalTime.of(13, 0)));
        ticket2.setSeat(seat1);
        ticket2.setTransaction(transaction1);
        ticket2.setTicketType(ticketType1);
        testEntityManager.persist(ticket2);
    }

    @Test
    public void whenFindUserByToken_thenReturnUser() {
        User user1 = userRepository.findUserByToken("5678");
        assertThat(user1.getUserId()).isEqualTo("1234");

        User user2 = userRepository.findUserByToken("test");
        assertThat(user2).isEqualTo(null);
    }

    @Test
    public void whenFindUserByUserId_thenReturnUser() {
        User user1 = userRepository.findUserByUserId("1234");
        assertThat(user1.getToken()).isEqualTo("5678");

        User user2 = userRepository.findUserByUserId("abcd");
        assertThat(user2.getToken()).isEqualTo("efgh");

        User user3 = userRepository.findUserByUserId("test");
        assertThat(user3).isEqualTo(null);
    }

    @Test
    public void whenFindTransactionById_thenReturnTransaction() {
        List<DbTransaction> transactions = transactionRepository.findAll();
        assertThat(transactions.size()).isEqualTo(1);
        assertThat(transactions.get(0).getStatus()).isEqualTo(DbTransaction.Status.COMPLETED);
        assertThat(transactions.get(0).getUser().getUserId()).isEqualTo("1234");
    }

    @Test
    public void whenFindUserTicketByTransacion_thenReturnUserTicket() {
        List<DbTransaction> transactions = transactionRepository.findAll();
        List<UserTicket> tickets = userTicketRepository.getByTransactionId(transactions.get(0));
        assertThat(tickets.size()).isEqualTo(1);
    }

    @Test
    public void whenFindUserTicketByUser_thenReturnUserTicket() {
        User user = userRepository.findUserByUserId("1234");
        List<UserTicket> tickets = userTicketRepository.getByUser(user);
        assertThat(tickets.size()).isEqualTo(1);
    }
}