package com.mozie;

import com.mozie.model.database.TicketType;
import com.mozie.repository.TicketTypeRepository;
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
public class TicketTypeRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Before
    public void setUp() {
        TicketType ticketType1 = new TicketType();
        ticketType1.setId(1);
        ticketType1.setName("NORMAL");
        ticketType1.setPrice(1950);
        ticketType1.setType("2d");
        testEntityManager.persist(ticketType1);

        TicketType ticketType2 = new TicketType();
        ticketType2.setId(2);
        ticketType2.setName("STUDENT");
        ticketType2.setPrice(1500);
        ticketType2.setType("2d");
        testEntityManager.persist(ticketType2);

        TicketType ticketType3 = new TicketType();
        ticketType3.setId(3);
        ticketType3.setName("NORMAL");
        ticketType3.setPrice(2100);
        ticketType3.setType("3d");
        testEntityManager.persist(ticketType3);

        TicketType ticketType4 = new TicketType();
        ticketType4.setId(4);
        ticketType4.setName("STUDENT");
        ticketType4.setPrice(1650);
        ticketType4.setType("3d");
        testEntityManager.persist(ticketType4);
    }

    @Test
    public void whenFindById_thenReturnTicketType() {
        TicketType ticketType = ticketTypeRepository.findById(1).get();
        assertThat(ticketType.getName()).isEqualTo("NORMAL");
        assertThat(ticketType.getPrice()).isEqualTo(1950);

        TicketType ticketType2 = ticketTypeRepository.findById(3).get();
        assertThat(ticketType2.getName()).isEqualTo("NORMAL");
        assertThat(ticketType2.getPrice()).isEqualTo(2100);
    }

    @Test
    public void whenFindAllByType_thenReturnTicketTypes() {
        List<TicketType> result = ticketTypeRepository.getAllByType("2d");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getType()).isEqualTo("2d");
    }
}