package it.univr.satella.service;

import it.univr.satella.model.Sensor;
import it.univr.satella.model.SlotCapabilities;
import it.univr.satella.repository.SensorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
    "filepath.sensors=src/test/resources/sensors.json"
})
public class SensorServiceTest {

    @Autowired private Environment environment;
    @Autowired private SensorRepository sensorRepository;

    private SensorService sensorService;

    @Before
    public void setUp() throws Exception {
        sensorService = new SensorService(environment, sensorRepository);
    }

    @After
    public void tearDown() throws Exception {
        sensorRepository.deleteAll();
    }

    @Test
    public void shouldLoadSensorsAtDefaultPath() {
        sensorService.loadSensorsAtDefaultPath();

        List<Sensor> sensorList = sensorRepository.findAll();
        assertEquals(2, sensorList.size());

        assertTrue(sensorList.stream().anyMatch(x -> x.getModelName().equals("sensor-1")));
        assertTrue(sensorList.stream().anyMatch(x -> x.getModelName().equals("sensor-2")));
    }

    @Test
    public void shouldReadSensors() {
        List<Sensor> sensorList = sensorService.readSensors("src/test/resources/sensors.json");
        assertEquals(2, sensorList.size());

        assertTrue(sensorList.stream().anyMatch(x -> x.getModelName().equals("sensor-1")));
        assertTrue(sensorList.stream().anyMatch(x -> x.getModelName().equals("sensor-2")));
    }

    @Test
    public void shouldNotReadSensors() {
        List<Sensor> sensorList = sensorService.readSensors("src/test/resources/invalid.json");
        assertEquals(0, sensorList.size());
    }

    @Test
    public void shouldFindAllCompatible() {
        sensorRepository.saveAll(List.of(
                new Sensor("not-compatible", 5.0f, 10.0f, 5.0f, 10.0f),
                new Sensor("compatible", 0.0f, 10.0f, 0.0f, 10.0f)
        ));

        SlotCapabilities capabilities = new SlotCapabilities(1.0f, 1.0f);
        List<Sensor> compatibleSensor = sensorService.findAllCompatible(capabilities);

        assertEquals(1, compatibleSensor.size());
        assertEquals("compatible", compatibleSensor.get(0).getModelName());
    }
}