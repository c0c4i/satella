package it.univr.satella.drivers;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for loading and storing all published sensor drivers
 */
@Repository
public class SensorDriverRepository {

    /**
     * All published sensor drivers
     */
    @Autowired
    @SensorDriverPublish
    private List<SensorDriver> publishedSensorDrivers;

    @PostConstruct
    public void printSensorDrivers() {
        Logger log = LoggerFactory.getLogger(SensorDriverRepository.class);
        for (ISensorDriver driver : publishedSensorDrivers)
            log.info("Found driver: " + driver.getId());
    }

    /**
     * Returns a published driver by its id
     * @param id The driver name and its version, for example drok-driver[0.0.1]
     */
    public Optional<SensorDriver> getDriver(String id) {
        return publishedSensorDrivers.stream()
                .filter(driver -> driver.getId().equals(id))
                .findFirst();
    }
}




