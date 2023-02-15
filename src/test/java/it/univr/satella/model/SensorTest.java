package it.univr.satella.model;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.Assert.*;

public class SensorTest {

    @Test
    public void shouldBeValid() {
        Sensor sensor = new Sensor("sensor", 0.0f, 1.0f, 0.0f, 1.0f);
        assertEquals(-1, sensor.isValid());
    }

    @Test
    public void shouldNotBeValidForModelName() {
        Sensor sensor = new Sensor("s e n s o r", 0.0f, 1.0f, 0.0f, 1.0f);
        assertEquals(2, sensor.isValid());
    }

    @Test
    public void shouldNotBeValidForVoltage() {
        Sensor sensor = new Sensor("sensor", 5.0f, 1.0f, 0.0f, 1.0f);
        assertEquals(3, sensor.isValid());
    }

    @Test
    public void shouldNotBeValidForAmperage() {
        Sensor sensor = new Sensor("sensor", 0.0f, 1.0f, 5.0f, 1.0f);
        assertEquals(4, sensor.isValid());
    }

    @Test
    public void shouldBeCompatible() {
        SlotCapabilities capabilities = new SlotCapabilities(5.0f, 3.0f);
        Sensor sensor = new Sensor("sensor", 3.0f, 3.0f, 4.0f, 6.0f);
        assertEquals(-1, sensor.isCompatible(capabilities));
    }

    @Test
    public void shouldNotBeCompatible() {
        SlotCapabilities capabilities = new SlotCapabilities(5.0f, 3.0f);
        Sensor sensor = new Sensor("sensor", 4.0f, 4.0f, 4.0f, 5.0f);
        assertNotEquals(-1, sensor.isCompatible(capabilities));
    }
}