package it.univr.satella.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.model.Slot;
import it.univr.satella.model.SlotCapabilities;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Manages all slots
 */
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

        }
    }

}
