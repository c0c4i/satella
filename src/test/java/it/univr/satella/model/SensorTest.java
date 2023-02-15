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
    public void shouldNotBeValidForAmperage() {
        Sensor sensor = new Sensor("sensor", 0.0f, 1.0f, 5.0f, 1.0f);
        assertEquals(3, sensor.isValid());
    }

    @Test
    public void shouldNotBeValidForVoltage() {
        Sensor sensor = new Sensor("sensor", 5.0f, 1.0f, 0.0f, 1.0f);
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

    @Test
    public void shouldGet() {
        Sensor sensor = new Sensor("sensor", 2.0f, 3.0f, 4.0f, 5.0f);
        assertEquals("sensor", sensor.getModelName());
        assertEquals(2.0f, sensor.getMinVoltage(), 0.0f);
        assertEquals(3.0f, sensor.getMaxVoltage(), 0.0f);
        assertEquals(4.0f, sensor.getMinAmperage(), 0.0f);
        assertEquals(5.0f, sensor.getMaxAmperage(), 0.0f);
    }

    @Test
    public void shouldGetInvalidMessage() {
        assertNull(Sensor.getInvalidMessage(-1));
        assertNull(Sensor.getInvalidMessage(-5));
        assertEquals(Sensor.getInvalidMessage(1), "Il modello non può essere vuoto!");
        assertEquals(Sensor.getInvalidMessage(2), "Il modello non può contenere spazi vuoti!");
        assertEquals(Sensor.getInvalidMessage(3), "Il campo \"Amperaggio minimo\" deve essere inferiore al campo \"Amperaggio massimo\"");
        assertEquals(Sensor.getInvalidMessage(4), "Il \"Voltaggio minimo\" deve essere inferiore al \"Voltaggio massimo\"");
    }

    @Test
    public void shouldGetIncompatibleMessage() {
        SlotCapabilities capabilities = new SlotCapabilities(5.0f, 3.0f);
        assertNull(Sensor.getIncompatibleMessage(4, capabilities));
        assertNull(Sensor.getIncompatibleMessage(9, capabilities));
        assertEquals(Sensor.getIncompatibleMessage(5, capabilities), "Il campo \"Amperaggio minimo\" deve inferiore o uguale alla capacità dello slot a cui è collegato (5.0A)");
        assertEquals(Sensor.getIncompatibleMessage(6, capabilities), "Il campo \"Amperaggio massimo\" deve superiore o uguale alla capacità dello slot a cui è collegato (5.0A)");
        assertEquals(Sensor.getIncompatibleMessage(7, capabilities), "Il \"Voltaggio minimo\" deve inferiore o uguale alla capacità dello slot a cui è collegato (3.0V)");
        assertEquals(Sensor.getIncompatibleMessage(8, capabilities), "Il \"Voltaggio massimo\" deve superiore o uguale alla capacità dello slot a cui è collegato (3.0V)");
    }
}