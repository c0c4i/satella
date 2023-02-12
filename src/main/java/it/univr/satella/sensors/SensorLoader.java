package it.univr.satella.sensors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Loader responsible of loading all sensor descriptors
 */
@Component
public class SensorLoader {

    static Logger log = LoggerFactory.getLogger(SensorLoader.class);
    private final SensorRepository sensorRepository;

    @Autowired
    private Environment env;

    @Autowired
    public SensorLoader(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @PostConstruct
    public void LoadSensors() {
        String filepath = env.getProperty("filepath.sensors");
        loadSensorsAt(filepath);
    }

    /**
     * Inserts in the repository all descriptors
     * in the sensors.json file
     */
    public void loadSensorsAt( String filepath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SensorDescriptor[] values = mapper.readValue(
                    Paths.get(filepath).toFile(),
                    SensorDescriptor[].class
            );

            for (SensorDescriptor sensor : values)
                addSensorToRepo(sensor);

        } catch (IOException e) {
            log.warn("Unable to load sensors in the sensors.json file");
        }
    }

    public void addSensorToRepo(SensorDescriptor sensor) {
        if (sensor.isValid()) {
            log.info("Found valid sensor descriptor: " + sensor.getModel());
            sensorRepository.save(sensor);
        }
        else {
            log.warn("Found invalid sensor descriptor: " + sensor.getModel());
        }
    }
}
