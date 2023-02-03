package it.univr.satella.sensors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Repository responsible for storing and retrieving all
 * known sensor descriptors
 */
@Component
public class SensorRepository {
    static Logger log = LoggerFactory.getLogger(SensorRepository.class);

    /**
     * All known sensors
     */
    private List<SensorDescriptor> sensorDescriptorList = new ArrayList<>();

    /**
     * Construct the repository by loading all descriptors
     * in the sensors.json file
     */
    public SensorRepository(@Value("${filepath.sensors}") String filepath)
    throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        SensorDescriptor[] values = mapper.readValue(
                Paths.get(filepath).toFile(),
                SensorDescriptor[].class
        );
        for (SensorDescriptor value : values) {
            log.info("Found sensor descriptor: " + value.getModel());
            sensorDescriptorList.add(value);
        }
    }

    /**
     * Retrieves a sensor descriptor by its model
     */
    public Optional<SensorDescriptor> getByModel(String model) {
        return sensorDescriptorList.stream()
                .filter(x -> x.getModel().equals(model))
                .findFirst();
    }

    public List<SensorDescriptor> getAll() {
        return sensorDescriptorList;
    }
}
