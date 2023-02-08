package it.univr.satella;

import it.univr.satella.sensors.MeasureType;
import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.sensors.SensorRepository;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class SensorRepositoryTest {

    static String SENSORS_CORRECT = "src/test/resources/sensors/correct.json";
    static String SENSORS_INCORRECT = "src/test/resources/sensors/unit_error.json";

    static SensorRepository repository;

    @BeforeClass
    public static void initialize() throws IOException {
        repository = new SensorRepository(SENSORS_CORRECT);
    }

    @Test
    public void testGetAll() {
        List<SensorDescriptor> values = repository.getAll();
        assertEquals(2, values.size());
    }

    @Test
    public void testGetByModel() {
        assertTrue(repository.getByModel("sensor-1").isPresent());
        assertTrue(repository.getByModel("sensor-Z").isEmpty());
    }

    @Test
    public void testDeserialization() {
        SensorDescriptor sensor = repository.getByModel("sensor-1").get();

        assertEquals("sensor-1", sensor.getModel());
        assertEquals("vendor-1", sensor.getVendor());
        assertEquals(MeasureType.Temperature, sensor.getMeasureUnit());
        assertEquals("driver[0.0.1]", sensor.getDriver());
        assertEquals(1.0, sensor.getMinVoltage(), 0.0);
        assertEquals(8.0, sensor.getMaxVoltage(), 0.0);
        assertEquals(2.0, sensor.getMinAmperage(), 0.0);
        assertEquals(4.0, sensor.getMaxAmperage(), 0.0);
        assertEquals(-45.0, sensor.getMinMeasureValue(), 0.0);
        assertEquals(80.0, sensor.getMaxMeasureValue(), 0.0);
    }

    @Test(expected = IOException.class)
    public void testConstructorInvalidFile() throws IOException {
        SensorRepository repo = new SensorRepository(SENSORS_INCORRECT);
    }
}
