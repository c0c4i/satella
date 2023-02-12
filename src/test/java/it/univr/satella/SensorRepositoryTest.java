package it.univr.satella;

import it.univr.satella.sensors.SampleUnit;
import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.sensors.SensorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SensorRepositoryTest {

    @Autowired
    private SensorRepository repository;

    @Before
    public void initialize() {
        repository.save(new SensorDescriptor("sensor-1", "v", "d", SampleUnit.Pressure, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
        repository.save(new SensorDescriptor("sensor-2", "v", "d", SampleUnit.Pressure, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
    }

    @Test
    public void testFindByModel() {
        assertNotNull(repository.findByModel("sensor-1"));
    }

    @Test
    public void testFindAll() {
        List<SensorDescriptor> values = repository.findAll();
        assertEquals(2, values.size());

        assertTrue(values.stream().anyMatch(x -> x.getModel().equals("sensor-1")));
        assertTrue(values.stream().anyMatch(x -> x.getModel().equals("sensor-2")));
    }
}
