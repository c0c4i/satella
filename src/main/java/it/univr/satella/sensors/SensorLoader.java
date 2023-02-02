package it.univr.satella.sensors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.descriptors.SensorDescriptor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;


/**
 * This component is responsible for loading all sensor descriptors
 * present in the sensors.json configuration file
 */
@Component
public class SensorLoader {

    @Autowired
    private SensorRepository sensorRepository;

    @PostConstruct
    public void loadSensors() throws IOException {
        Logger log = LoggerFactory.getLogger(SensorLoader.class);

        ObjectMapper mapper = new ObjectMapper();
        SensorDescriptor[] sensorDescriptors = mapper
                .readValue(Paths.get("src/main/resources/examples/sensors.json").toFile(), SensorDescriptor[].class);

        for (SensorDescriptor value : sensorDescriptors) {
            log.info("Found sensor descriptor: " + value.getModel());

            // TODO
            //sensorRepository.save(value);
        }
    }
}
