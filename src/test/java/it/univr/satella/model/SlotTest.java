package it.univr.satella.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SlotTest {

    @Test
    public void shouldBeEmpty() {
        Slot slot = new Slot();
        assertEquals(slot.getSlot(), 0);
        assertNull(slot.getCapabilities());
        assertNull(slot.getAttachedSensor());
    }

    @Test
    public void shouldReturnSlot() {
        Slot slotPort = new Slot(0, new SlotCapabilities(2.0f, 5.0f));
        assertEquals(slotPort.getSlot(), 0);
    }

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
