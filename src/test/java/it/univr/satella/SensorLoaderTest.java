package it.univr.satella;

import it.univr.satella.sensors.SampleUnit;
import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.sensors.SensorLoader;

import it.univr.satella.sensors.SensorRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test the integration between the sensor loader and the sensor repository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SensorLoaderTest {

    // Instance of the JPA repository
    @Autowired private SensorRepository sensorRepository;

    @Test
    public void testLoaderValid() {

        // Load the correct sensors
        SensorLoader sensorLoader = new SensorLoader(sensorRepository);
        sensorLoader.loadSensorsAt("src/test/resources/sensors/correct.json");

        // Check that all sensors have been loaded
        List<SensorDescriptor> sensors = sensorRepository.findAll();
        assertEquals(2, sensors.size());

        assertNotNull(sensorRepository.findByModel("sensor-1"));
        assertNotNull(sensorRepository.findByModel("sensor-2"));

        SensorDescriptor sensor = sensorRepository.findByModel("sensor-1");
        assertEquals("sensor-1", sensor.getModel());
        assertEquals("vendor-1", sensor.getVendor());
        assertEquals(SampleUnit.Temperature, sensor.getMeasureUnit());
        assertEquals("driver[0.0.1]", sensor.getDriver());
        assertEquals(1.0, sensor.getMinVoltage(), 0.0);
        assertEquals(8.0, sensor.getMaxVoltage(), 0.0);
        assertEquals(2.0, sensor.getMinAmperage(), 0.0);
        assertEquals(4.0, sensor.getMaxAmperage(), 0.0);
        assertEquals(-45.0, sensor.getMinMeasureValue(), 0.0);
        assertEquals(80.0, sensor.getMaxMeasureValue(), 0.0);
    }

    @Test
    public void testLoaderInvalid() {

        // Load the incorrect sensors
        SensorLoader sensorLoader = new SensorLoader(sensorRepository);
        sensorLoader.loadSensorsAt("src/test/resources/sensors/incorrect.json");

        assertNull(sensorRepository.findByModel("sensor-invalid-measure"));
        assertNull(sensorRepository.findByModel("sensor-invalid-voltage"));
        assertNull(sensorRepository.findByModel("sensor-invalid-amperage"));
        assertNull(sensorRepository.findByModel("sensor-invalid-limits"));
    }
}
