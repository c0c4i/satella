package it.univr.satella.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.model.Sensor;
import it.univr.satella.model.Slot;
import it.univr.satella.model.SlotCapabilities;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * Manages all slots
 */
@Service
public class SlotService {

    private final Environment environment;
    private HashMap<Integer, Slot> slots;

    @Autowired
    public SlotService(Environment environment)
    {
        this.environment = environment;
        this.slots = new HashMap<>();
    }

    @PostConstruct
    public void loadSlotsAtDefaultPath() {
        String filepath = environment.getProperty("filepath.slots");
        if (filepath != null)
            loadSlots(filepath);
    }

    /**
     * Load all slots present in the filepath
     * @param filepath Filepath of the file to load
     */
    public void loadSlots(String filepath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SlotCapabilities[] slotCapabilities = mapper.readValue(
                    Paths.get(filepath).toFile(),
                    SlotCapabilities[].class
            );

            slots = new HashMap<>();
            for (int id = 0; id < slotCapabilities.length; id++)
                slots.put(id, new Slot(id, slotCapabilities[id]));

        }
        catch (IOException e) {
            int a = 0;
            // TODO
        }
    }

    public List<Slot> getSlots() {
        return slots.values().stream().toList();
    }

    public Slot getSlotByPort(int port) {
        return slots.get(port);
    }

    public SlotCapabilities getSlotCapabilitiesFromSensor(Sensor sensor) {
        List<Slot> result = slots.values().stream().filter(slot -> {
            Sensor attached = slot.getAttachedSensor();
            if(attached == null) return false;
            return attached.getModelName().equals(sensor.getModelName());
        }).toList();
        if(result.size() == 0) return null;
        return result.get(0).getCapabilities();
    }


    /**
     * Manually set a slot
     */
    public void setSlot(int id, Slot slot) {
        slots.put(id, slot);
    }

    /**
     * Tries to attach a sensor to a slot in the station
     * @param slotID ID of the target slot
     * @param sensor sensor to be attached
     * @return true un success, false otherwise
     */
    public boolean attachSensorToSlot(int slotID, Sensor sensor) {

        // Get target slot
        if (slots.containsKey(slotID)) {
            Slot targetSlot = slots.get(slotID);
            return targetSlot.attachSensor(sensor);
        }
        return false;
    }

    /**
     * Tries to detach a sensor from a slot
     * @param slotID ID of the target slot
     * @return true on success, false otherwise
     */
    public boolean detachSensorFromSlot(int slotID) {
        if (slots.containsKey(slotID)) {
            Slot slot = slots.get(slotID);
            slot.detachSensor();
            return true;
        }
        return false;
    }
}
