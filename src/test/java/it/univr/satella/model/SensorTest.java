package it.univr.satella.model;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.Assert.*;

public class SensorTest {

    @Test
    public void shouldBeValid() {
        Sensor sensor = new Sensor("sensor", 0.0f, 1.0f, 0.0f, 1.0f);
        assertTrue(sensor.isValid());
    }

    @Test
    public void shouldNotBeValidForModelName() {
        Sensor sensor = new Sensor(null, 0.0f, 1.0f, 0.0f, 1.0f);
        assertFalse(sensor.isValid());
    }

    @Test
    public void shouldNotBeValidForVoltage() {
        Sensor sensor = new Sensor("sensor", 5.0f, 1.0f, 0.0f, 1.0f);
        assertFalse(sensor.isValid());
    }

    @Test
    public void shouldNotBeValidForAmperage() {
        Sensor sensor = new Sensor("sensor", 0.0f, 1.0f, 5.0f, 1.0f);
        assertFalse(sensor.isValid());
    }

    @Test
    public void shouldBeCompatible() {
        SlotCapabilities capabilities = new SlotCapabilities(5.0f, 3.0f);
        Sensor sensor = new Sensor("sensor", 3.0f, 3.0f, 4.0f, 6.0f);
        assertTrue(sensor.isCompatible(capabilities));
    }

    @Test
    public void shouldNotBeCompatible() {
        SlotCapabilities capabilities = new SlotCapabilities(5.0f, 3.0f);
        Sensor sensor = new Sensor("sensor", 4.0f, 4.0f, 4.0f, 5.0f);
        assertFalse(sensor.isCompatible(capabilities));
    }
}