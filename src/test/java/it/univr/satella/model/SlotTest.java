package it.univr.satella.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SlotTest {

    @Test
    public void shouldAttachSensor() {
        Slot slot = new Slot(0, new SlotCapabilities(2.0f, 5.0f));
        Sensor sensor = new Sensor("sensor", 0.0f, 10.0f, 0.0f, 10.0f);
        assertTrue(slot.attachSensor(sensor));
    }

    @Test
    public void shouldNotAttachSensor() {
        Slot slot = new Slot(0, new SlotCapabilities(2.0f, 5.0f));
        Sensor sensor = new Sensor("sensor", 0.0f, 1.0f, 0.0f, 1.0f);
        assertFalse(slot.attachSensor(sensor));
    }

}
