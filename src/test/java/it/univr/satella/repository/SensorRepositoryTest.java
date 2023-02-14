package it.univr.satella.repository;

import it.univr.satella.model.Sensor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Before
    public void setUp() throws Exception {
        sensorRepository.save(new Sensor("sensor-1", 0.0f, 0.0f, 0.0f, 0.0f));
        sensorRepository.save(new Sensor("sensor-2", 0.0f, 0.0f, 0.0f, 0.0f));
        sensorRepository.save(new Sensor("sensor-3", 0.0f, 0.0f, 0.0f, 0.0f));
    }

    @After
    public void tearDown() throws Exception {
        sensorRepository.deleteAll();
    }

    @Test
    public void shouldFindByModelName() {
        assertNotNull(sensorRepository.findByModelName("sensor-2"));
    }

    @Test
    public void shouldFindAll() {
        List<Sensor> sensors = sensorRepository.findAll();
        assertTrue(sensors.stream().anyMatch(x -> x.getModelName().equals("sensor-1")));
        assertTrue(sensors.stream().anyMatch(x -> x.getModelName().equals("sensor-2")));
        assertTrue(sensors.stream().anyMatch(x -> x.getModelName().equals("sensor-3")));
    }
}