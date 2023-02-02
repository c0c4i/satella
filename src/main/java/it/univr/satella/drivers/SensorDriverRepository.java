package it.univr.satella.drivers;

import it.univr.satella.drivers.ISensorDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * This repo contains all published sensor drivers
 */
@Component
public class SensorDriverRepository {

    /**
     * All published sensor drivers
     */
    @Autowired
    @SensorDriverPublish
    private List<ISensorDriver> sensorDriverList;

    /**
     * Returns a published driver by its id
     * @param id The driver name and its version, for example drok-driver[0.0.1]
     */
    public Optional<ISensorDriver> getDriver(String id) {
        return sensorDriverList.stream()
                .filter(driver -> driver.getId().equals(id))
                .findFirst();
    }
}




