package it.univr.satella;

import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SampleRepository;
import it.univr.satella.sensors.SampleUnit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SampleRepositoryTest {

    @Autowired
    private SampleRepository repository;

    @Before
    public void initialize() {
        assertNotNull(repository);

        repository.save(new Sample(1,
                LocalDateTime.of(2015, Month.JUNE, 29, 19, 30, 40),
                SampleUnit.Temperature, 2.0f)
        );
        repository.save(new Sample(1,
                LocalDateTime.of(2015, Month.JUNE, 29, 19, 30, 39),
                SampleUnit.Temperature, 1.0f)
        );
        repository.save(new Sample(1,
                LocalDateTime.of(2015, Month.JUNE, 29, 19, 30, 41),
                SampleUnit.Temperature, 3.0f)
        );
        repository.save(new Sample(2,
                LocalDateTime.of(2015, Month.JUNE, 29, 19, 30, 41),
                SampleUnit.Temperature, 3.0f)
        );
    }

    @Test
    public void testFindAllBySensorIdAndTimeBetweenOrderByTime() {
        List<Sample> result = repository.findAllBySensorIdAndTimeBetweenOrderByTime(1,
                LocalDateTime.of(2015, Month.JUNE, 29, 19, 30, 10),
                LocalDateTime.of(20115, Month.JUNE, 29, 19, 30, 50));

        assertEquals(3, result.size());

        // Check the order of the results
        assertTrue(result.get(0).time.isBefore(result.get(1).time));
        assertTrue(result.get(1).time.isBefore(result.get(2).time));

        // They must refer to the same sensor
        for (Sample sample : result) {
            if (sample.sensorId != 1)
                fail();
        }
    }

}
