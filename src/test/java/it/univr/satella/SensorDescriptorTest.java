package it.univr.satella;

import it.univr.satella.sensors.MeasureType;
import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.sensors.SensorRepository;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Checks that a descriptor correctly evaluated to valid
 */
public class SensorDescriptorTest {

    @Test
    public void testIsValid() {
        SensorDescriptor validSensor = new SensorDescriptor(
                "sensor", "vendor", "driver[0.0.0]",
                MeasureType.Temperature,
                0.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 1.0f
        );
        assertTrue(validSensor.isValid());
    }

    @Test
    public void testIsValid_measureUnit() {
        SensorDescriptor validSensor = new SensorDescriptor(
                "sensor", "vendor", "driver[0.0.0]",
                null,
                0.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 1.0f
        );
        assertFalse(validSensor.isValid());
    }

    @Test
    public void testIsValid_model() {
        SensorDescriptor validSensor = new SensorDescriptor(
                null, "vendor", "driver[0.0.0]",
                MeasureType.Temperature,
                0.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 1.0f
        );
        assertFalse(validSensor.isValid());
    }
    @Test
    public void testIsValid_vendor() {
        SensorDescriptor validSensor = new SensorDescriptor(
                "sensor", null, "driver[0.0.0]",
                MeasureType.Temperature,
                0.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 1.0f
        );
        assertFalse(validSensor.isValid());
    }

    @Test
    public void testIsValid_driver() {
        SensorDescriptor validSensor = new SensorDescriptor(
                "sensor", "vendor", null,
                MeasureType.Temperature,
                0.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 1.0f
        );
        assertFalse(validSensor.isValid());
    }

    @Test
    public void testIsValid_value() {
        SensorDescriptor validSensor = new SensorDescriptor(
                "sensor", "vendor", "driver[0.0.0]",
                MeasureType.Temperature,
                10.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 1.0f
        );
        assertFalse(validSensor.isValid());
    }

    @Test
    public void testIsValid_voltage() {
        SensorDescriptor validSensor = new SensorDescriptor(
                "sensor", "vendor", "driver[0.0.0]",
                MeasureType.Temperature,
                0.0f, 1.0f,
                10.0f, 1.0f,
                0.0f, 1.0f
        );
        assertFalse(validSensor.isValid());
    }

    @Test
    public void testIsValid_amperage() {
        SensorDescriptor validSensor = new SensorDescriptor(
                "sensor", "vendor", "driver[0.0.0]",
                MeasureType.Temperature,
                0.0f, 1.0f,
                0.0f, 1.0f,
                10.0f, 1.0f
        );
        assertFalse(validSensor.isValid());
    }
}
