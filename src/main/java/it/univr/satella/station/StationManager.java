package it.univr.satella.station;

import ch.qos.logback.core.joran.sanity.Pair;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.drivers.ISensorDriver;
import it.univr.satella.drivers.SensorDriverRepository;
import it.univr.satella.sensors.MeasureType;
import it.univr.satella.sensors.SensorBundle;
import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.sensors.SensorRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class StationManager {

    static Logger log = LoggerFactory.getLogger(StationManager.class);
    @Autowired private Environment env;

    @Autowired private SensorDriverRepository sensorDriverRepository;
    @Autowired private SensorRepository sensorRepository;

    /**
     * All currently attached sensors
     */
    private HashMap<Integer, SensorBundle> sensorBundles = new HashMap<>();
    private final StationDescriptor descriptor;

    /**
     * Constructs the station
     * @param filepath Filepath of the station descriptor file
     */
    public StationManager(@Value("${filepath.station}") String filepath)
    throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        descriptor = mapper.readValue(
                Paths.get(filepath).toFile(),
                StationDescriptor.class
        );

        log.info("Successfully loaded station descriptor");
        List<SlotDescriptor> slots = descriptor.getSlots();
        for (int i = 0; i < slots.size(); i++) {
            SlotDescriptor slotDescriptor = slots.get(i);
            log.info("Slot " + i + " (voltage: " + slotDescriptor.getVoltage()
                    + ", amperage: " + slotDescriptor.getAmperage() + ")");
        }
    }

    /**
     * Tries to attach a sensor to a slot in the station
     * @param slot Where to attach the sensor
     * @param sensorModel Model of the sensor to attach
     */
    public void attach(int slot, String sensorModel)
    throws Exception
    {
        // Check that the sensor exists
        Optional<SensorDescriptor> sensorOpt = sensorRepository.getByModel(sensorModel);
        if (sensorOpt.isEmpty()) throw new Exception("Sensor doesn't exists");
        SensorDescriptor sensor = sensorOpt.get();

        // Check that the slot exists
        List<SlotDescriptor> slots = descriptor.getSlots();
        if (slots.size() < slot)
            throw new Exception("Invalid slot");

        // Check that the slot is compatible with the sensor
        SlotDescriptor slotDescriptor = slots.get(slot);
        if (!slotDescriptor.isCompatible(sensor))
            throw new Exception("Sensor is not compatible with slot");

        // Retrieve associated driver
        Optional<ISensorDriver> driverOpt = sensorDriverRepository.getDriver(sensor.getDriver());
        if (driverOpt.isEmpty()) throw new Exception("Driver doesn't exists");
        ISensorDriver driver = driverOpt.get();

        // Check that the driver is compatible with the sensor
        if (!driver.isCompatible(sensor))
            throw new Exception("Driver is not compatible with sensor");

        // Removes old attached sensor if present
        if (sensorBundles.containsKey(slot)) {
            SensorBundle bundle = sensorBundles.get(slot);
            bundle.shutdown();
        }

        SensorBundle bundle = new SensorBundle(sensor, driver, slot);
        sensorBundles.put(slot, bundle);
        bundle.initialize();
    }

    private static class SlotConfig {
        @JsonProperty("sensor")
        public String sensorModel;
        @JsonProperty
        public int slot;
    }

    /**
     * Load the default configuration file
     */
    @PostConstruct
    public void loadConfiguration() throws Exception {
        String filepath = env.getProperty("filepath.configuration");
        loadConfigurationAt(filepath);
    }

    /**
     * Load the configuration file and tries to attach all sensors
     * @param filepath Filepath of the configuration file
     */
    public void loadConfigurationAt(String filepath)
    throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        SlotConfig[] cons = mapper.readValue(
                Paths.get(filepath).toFile(),
                SlotConfig[].class
        );

        for (SlotConfig con : cons) {
            attach(con.slot, con.sensorModel);
            log.info("Successfully attached sensor " + con.sensorModel + " at " + con.slot);
        }
    }

    /**
     * Samples all attached sensors and generates a list
     * of measurements
     */
    public List<Pair<MeasureType, Float>> measure() {
        List<Pair<MeasureType, Float>> result = new ArrayList<>();
        for (SensorBundle sensor : sensorBundles.values()) {
            Optional<Pair<MeasureType, Float>> valueOpt = sensor.measure();
            valueOpt.ifPresent(result::add);
        }
        return result;
    }
}
