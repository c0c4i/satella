package it.univr.satella.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.model.Sensor;
import it.univr.satella.model.SlotCapabilities;
import it.univr.satella.repository.SensorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Loader responsible of loading all sensor descriptors
 */
@Service
public class SensorService {

    private final Environment environment;
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(
            Environment environment,
            SensorRepository sensorRepository)
    {
        this.sensorRepository = sensorRepository;
        this.environment = environment;
    }

    @PostConstruct
    public void loadSensorsAtDefaultPath() {
        String filepath = environment.getProperty("filepath.sensors");
        List<Sensor> sensors = readSensors(filepath);
        for (Sensor sensor : sensors)
            if (sensor.isValid())
                sensorRepository.save(sensor);
    }

    /**
     * Inserts in the repository all descriptors
     * in the sensors.json file
     */
    public List<Sensor> readSensors(String filepath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Sensor[] values = mapper.readValue(
                    Paths.get(filepath).toFile(),
                    Sensor[].class
            );

            return Arrays.stream(values).toList();


        } catch (IOException e) {
            // TODO
        }
        return new ArrayList<>();
    }

    /**
     * Returns all sensors compatible with a slot
     * @param capabilities Capabilities that need o be matched
     */
    public List<Sensor> findAllCompatible(SlotCapabilities capabilities) {
        return sensorRepository.findAll().stream()
                .filter(x -> x.isCompatible(capabilities))
                .toList();
    }
}
