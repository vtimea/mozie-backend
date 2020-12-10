package com.mozie;

import com.mozie.model.database.User;
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
}