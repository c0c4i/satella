package it.univr.satella.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.model.Sensor;
import it.univr.satella.model.SlotCapabilities;
import it.univr.satella.repository.SensorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
        if (filepath != null)
            loadSensors(filepath);
    }

    /**
     * Inserts in the repository all descriptors
     * in the sensors.json file
     */
    public boolean loadSensors(String filepath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Sensor[] sensors = mapper.readValue(
                    Paths.get(filepath).toFile(),
                    Sensor[].class
            );

            for (Sensor sensor : sensors)
                if (sensor.isValid() == -1)
                    sensorRepository.save(sensor);

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns all sensors compatible with a slot
     * @param capabilities Capabilities that need o be matched
     */
    public List<Sensor> findAllCompatible(SlotCapabilities capabilities) {
        return sensorRepository.findAll().stream()
                .filter(x -> x.isCompatible(capabilities) == -1)
                .toList();
    }

    public Sensor findSensorByModelName(String modelName) {
        return sensorRepository.findByModelName(modelName);
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public void addSensor(Sensor sensor) {
         sensorRepository.save(sensor);
    }

    public void deleteSensor(Sensor sensor) {
        sensorRepository.delete(sensor);
    }
}
