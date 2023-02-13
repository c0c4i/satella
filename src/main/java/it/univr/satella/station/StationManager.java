package it.univr.satella.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.alarm.Alarm;
import it.univr.satella.alarm.AlarmService;
import it.univr.satella.drivers.ISensorDriver;
import it.univr.satella.drivers.SensorDriverRepository;
import it.univr.satella.notification.NotificationService;
import it.univr.satella.sensors.*;
import it.univr.satella.station.exceptions.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class StationManager {

    static Logger log = LoggerFactory.getLogger(StationManager.class);
    @Autowired private Environment env;

    @Autowired
    private NotificationService notificationService;

    private final SensorDriverRepository sensorDriverRepository;
    private final SensorRepository sensorRepository;
    private final SampleRepository sampleRepository;
    private final AlarmService alarmService;

    /**
     * All currently attached sensors
     */
    private HashMap<Integer, SensorBundle> sensorBundles = new HashMap<>();
    private int lastSensorBundleId;

    /**
     * All available slots
     */
    private List<SlotDescriptor> slotDescriptorList;

    private int currentTimestamp;

    /**
     * Constructs the station
     * @param filepath Filepath of the station descriptor file
     */
    @Autowired
    public StationManager(@Value("${filepath.station}") String filepath,
                          SensorDriverRepository sensorDriverRepository,
                          SensorRepository sensorRepository,
                          SampleRepository sampleRepository,
                          AlarmService alarmService)
    {
        this.sensorDriverRepository = sensorDriverRepository;
        this.sensorRepository = sensorRepository;
        this.sampleRepository = sampleRepository;
        this.alarmService = alarmService;

        this.lastSensorBundleId = 0;
        this.currentTimestamp = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            slotDescriptorList = List.of(mapper.readValue(
                    Paths.get(filepath).toFile(),
                    SlotDescriptor[].class
            ));

            log.info("Successfully loaded station descriptor");
            for (int i = 0; i < slotDescriptorList.size(); i++) {
                SlotDescriptor slotDescriptor = slotDescriptorList.get(i);
                log.info("Slot " + i + " (voltage: " + slotDescriptor.getVoltage()
                        + ", amperage: " + slotDescriptor.getAmperage() + ")");
            }
        }
        catch (IOException e) {
            if (notificationService != null)
                notificationService.error("Unable to load station slots!");
            slotDescriptorList = new ArrayList<>();
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
        SensorDescriptor sensor = sensorRepository.findByModel(sensorModel);
        if (sensor == null) throw new SensorNotFoundException(sensorModel);

        // Check that the slot exists
        if (slotDescriptorList.size() < slot)
            throw new InvalidSlotException(slot);

        // Check that the slot is compatible with the sensor
        SlotDescriptor slotDescriptor = slotDescriptorList.get(slot);
        if (!slotDescriptor.isCompatible(sensor))
            throw new SensorNotCompatibleException(sensorModel, slot);

        // Retrieves associated driver
        Optional<ISensorDriver> driverOpt = sensorDriverRepository.getDriver(sensor.getDriver());
        if (driverOpt.isEmpty()) throw new DriverNotFoundException(sensor.getDriver());
        ISensorDriver driver = driverOpt.get();

        // Check that the driver is compatible with the sensor
        if (!driver.isCompatible(sensor))
            throw new DriverNotCompatibleException(sensorModel, sensor.getDriver());

        // Removes old attached sensor if present
        if (sensorBundles.containsKey(slot)) {
            SensorBundle bundle = sensorBundles.get(slot);
            bundle.shutdown();
        }

        SensorBundle bundle = new SensorBundle(lastSensorBundleId, sensor, driver, slot);
        lastSensorBundleId += 1;

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
            if (notificationService != null)
                notificationService.info("Successfully attached sensor " + con.sensorModel + " at " + con.slot);
        }
    }

    /**
     * Samples all attached sensors and generates a list
     * of measurements
     */
    @Scheduled(fixedDelay = 1000)
    public void measure() {
        for (SensorBundle sensor : sensorBundles.values()) {
            SensorDescriptor descriptor = sensor.getDescriptor();

            Optional<Float> valueOpt = sensor.measure();
            if (valueOpt.isPresent()) {
                float value = valueOpt.get();

                sensor.setLastValue(value);
                sampleRepository.save(
                        new Sample(sensor.getId(), LocalDateTime.now(), descriptor.getMeasureUnit(), value));

                // Create alarm if necessary
                if (descriptor.isAlarmValue(value))
                    alarmService.sendAlarm(new Alarm(descriptor.getModel(), sensor.getSlot(), value, LocalDateTime.now()));
            }
        }
        currentTimestamp += 1;
    }

    public List<SlotDescriptor> getSlotDescriptors() {
        return slotDescriptorList;
    }
}
