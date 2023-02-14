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
    private final HashMap<Integer, Slot> slots;

    @Autowired
    public SlotService(Environment environment)
    {
        this.environment = environment;
        this.slots = new HashMap<>();
    }

    @PostConstruct
    public void loadSlotsAtDefaultPath() {
        loadSlots(environment.getProperty("filepath.slots"));
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

            for (int id = 0; id < slotCapabilities.length; id++)
                slots.put(id, new Slot(id, slotCapabilities[id]));

        }
        catch (IOException e) {
            // TODO
        }
    }

    /**
     * Retrives all the loaded slots
     */
    public List<Slot> getSlots() {
        return slots.values().stream().toList();
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
            if (sensor.isCompatible(targetSlot.getCapabilities())) {
                targetSlot.attachSensor(sensor);
                return true;
            }
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
