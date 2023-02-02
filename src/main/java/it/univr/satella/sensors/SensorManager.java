package it.univr.satella.sensors;

import it.univr.satella.drivers.SensorDriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Component responsible for managing currently active
 * sensors in the station's slots.
 */
@Component
public class SensorManager {

    @Autowired
    private SensorDriverRepository driverRepository;

    @Autowired
    private SensorRepository sensorRepository;

    private static class SlotStatus {

    }

    /**
     * Map from slot id to the attached sensor
     */
    private HashMap<Integer, SlotStatus> slots;

}
