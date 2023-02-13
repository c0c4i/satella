package it.univr.satella.drivers;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository responsible for loading and storing all published sensor drivers
 */
@Repository
public class SensorDriverRepository {

    /**
     * All published sensor drivers
     */
    private List<ISensorDriver> publishedSensorDrivers = new ArrayList<>();

    @PostConstruct
    public void printSensorDrivers() {
        Logger log = LoggerFactory.getLogger(SensorDriverRepository.class);
        for (ISensorDriver driver : publishedSensorDrivers)
            log.info("Found driver: " + driver.getId());
    }

    @Autowired
    @SensorDriverPublish
    public SensorDriverRepository(List<ISensorDriver> publishedSensorDrivers) {
        saveAll(publishedSensorDrivers);
    }

    /**
     * Insert multiple drivers in the repository
     * @param drivers new drivers to insert
     */
    public void saveAll(List<ISensorDriver> drivers) {
        for (ISensorDriver driver : drivers)
            save(driver);
    }

    /**
     * Insert a new driver into the repository
     * @param driver new driver to insert
     */
    public void save(ISensorDriver driver) {
        publishedSensorDrivers.removeIf(x -> x.getId().equals(driver.getId()));
        publishedSensorDrivers.add(driver);
    }

    /**
     * Returns a clone of a published driver by its id
     * @param id The driver name and its version, for example drok-driver[0.0.1]
     */
    public Optional<ISensorDriver> getDriver(String id) {
        Optional<ISensorDriver> found = publishedSensorDrivers.stream()
                .filter(driver -> driver.getId().equals(id))
                .findFirst();

        // Create a clone
        return found.map(ISensorDriver::copy);
    }
}