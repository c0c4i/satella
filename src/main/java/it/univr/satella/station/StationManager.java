package it.univr.satella.station;

import it.univr.satella.drivers.SensorDriverRepository;
import it.univr.satella.sensors.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StationManager {

    @Autowired private SensorDriverRepository sensorDriverRepository;
    @Autowired private SensorRepository sensorRepository;

    public StationManager(@Value("") String )

}
