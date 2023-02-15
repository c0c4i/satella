package it.univr.satella.service;

import it.univr.satella.model.Sensor;
import it.univr.satella.model.Slot;
import it.univr.satella.model.SlotCapabilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "filepath.slots=src/test/resources/slots_default.json"
})
public class SlotServiceTest {

    @Autowired private Environment environment;
    private SlotService slotService;

    @Before
    public void setUp() throws Exception {
        slotService = new SlotService(environment);
    }

    @Test
    public void shouldLoadSlotsAtDefaultPath() {
        slotService.loadSlotsAtDefaultPath();
        List<Slot> slots = slotService.getSlots();
        assertEquals(2, slots.size());
    }

    @Test
    public void shouldLoadSlots() {
        slotService.loadSlots("src/test/resources/slots_other.json");
        List<Slot> slots = slotService.getSlots();
        assertEquals(1, slots.size());
        assertEquals(15.0f, slots.get(0).getCapabilities().voltage, 0.0f);
    }

    @Test
    public void shouldGetSlotByPort() {
        Slot slot0 = new Slot(0, new SlotCapabilities(5.0f, 2.0f));
        slotService.setSlot(0, slot0);
        assertEquals(slot0, slotService.getSlotByPort(0));
    }

    @Test
    public void shouldNotLoadSlots() {
        slotService.loadSlots("src/test/resources/invalid.json");
        List<Slot> slots = slotService.getSlots();
        assertEquals(0, slots.size());
    }

    @Test
    public void shouldAttachSensorToSlot() {

        Slot slot0 = new Slot(0, new SlotCapabilities(5.0f, 2.0f));
        slotService.setSlot(0, slot0);

        assertTrue(slotService.attachSensorToSlot(0,
                new Sensor("sensor", 0.0f, 10.0f, 0.0f, 10.0f)));

        // We should have added the sensor to slot 0
        List<Slot> slots = slotService.getSlots();
        assertTrue(slots.get(0).hasAttachedSensor());
        assertEquals("sensor", slots.get(0).getAttachedSensor().getModelName());
    }

    @Test
    public void shouldNotAttachSensorToSlotBecauseNoId() {
        Sensor sensor = new Sensor("sensor", 0.0f, 0.0f, 0.0f, 0.0f);
        assertFalse(slotService.attachSensorToSlot(0, sensor));
    }

    @Test
    public void shouldNotAttachSensorToSlotBecauseNotCompatible() {
        Slot slot0 = new Slot(0, new SlotCapabilities(5.0f, 3.0f));
        slotService.setSlot(0, slot0);
        Sensor sensor = new Sensor("sensor", 0.0f, 0.0f, 0.0f, 0.0f);
        assertFalse(slotService.attachSensorToSlot(0, sensor));
    }

    @Test
    public void shouldDetachSensorFromSlot() {

        Slot slot0 = new Slot(0, new SlotCapabilities(5.0f, 2.0f));
        slot0.attachSensor(new Sensor("sensor", 0.0f, 10.0f, 0.0f, 10.0f));
        slotService.setSlot(0, slot0);

        assertTrue(slotService.detachSensorFromSlot(0));

        // We should have removed the sensor to slot 0
        List<Slot> slots = slotService.getSlots();
        assertFalse(slots.get(0).hasAttachedSensor());
    }

    @Test
    public void shouldNotDetachSensorFromSlotBecauseNoId() {
        assertFalse(slotService.detachSensorFromSlot(0));
    }

    @Test
    public void shouldGetSlotCapabilitiesFromSensor() {

        Sensor sensor = new Sensor("sensor", 0.0f, 10.0f, 0.0f, 10.0f);
        SlotCapabilities capabilities = new SlotCapabilities(5.0f, 2.0f);

        Slot slot0 = new Slot(0, capabilities);
        slot0.attachSensor(sensor);
        slotService.setSlot(0, slot0);

        assertEquals(capabilities, slotService.getSlotCapabilitiesFromSensor(sensor));
    }
}