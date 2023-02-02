package it.univr.satella.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.descriptors.SensorDescriptor;
import it.univr.satella.descriptors.StationDescriptor;
import it.univr.satella.drivers.SensorDriver;
import it.univr.satella.drivers.SensorDriverRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Component that manages the sensors
 */
@Component
public class StationManager {

    private static class Slot {
        @JsonProperty
        public String sensor;
        @JsonProperty
        public int port;
    }

    static Logger log = LoggerFactory.getLogger(StationManager.class);

    @Autowired
    private SensorDriverRepository sensorDriverRepo;

    private StationDescriptor descriptor;

    private HashMap<Integer, SensorDriver> loadedSensors;

    /**
     * Loads all configuration files related to the station
     */
    @PostConstruct
    private void initialize() throws IOException {

        // Load station structure
        ObjectMapper mapper = new ObjectMapper();
        this.descriptor = mapper.readValue(
                Paths.get("src/main/resources/examples/station.json").toFile(),
                StationDescriptor.class
        );

        // Load sensors mapping
        List<Slot> slots = loadConfiguration("src/main/resources/examples/configuration.json");
        for (Slot slot : slots) {
            log.info("Slot usage found: (sensor: " + slot.sensor + ", port: " + slot.port + ")");

            // Check that the sensor exists
            // TODO

            // Check that the slot exists
            Optional<StationDescriptor.SlotDescriptor> caps = descriptor.getSlot(slot.port);
            if (caps.isEmpty()) {
                log.error("Unable to find usable port " + slot.port + " for " + slot.sensor);
                continue;
            }

            // Check that the driver exists
            Optional<SensorDriver> optionalSensorDriver = sensorDriverRepo.getDriver("" /* TODO */);
            if (optionalSensorDriver.isEmpty()) {
                log.error("Unable to find driver " + slot.port + " for " + slot.sensor);
                continue;
            }

            // Check that the slot is compatible with the sensor
            // TODO


            // Add to loaded sensors
            // TODO

        }
    }

    /**
     * Load a sensor configuration from a file
     * @return List of all the pair of port and sensor
     */
    public List<Slot> loadConfiguration(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return List.of(mapper.readValue(Paths.get(filepath).toFile(), Slot[].class));
    }
}
