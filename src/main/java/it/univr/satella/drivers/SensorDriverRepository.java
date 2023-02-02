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
    @Autowired
    //@SensorDriverPublish
    private List<SensorDriver> publishedSensorDrivers;

    /**
     * All published sensors ready to be instantiated
     */
    private List<BeanDefinition> publishedSensorDriverBeans;

    @PostConstruct
    public void printSensorDrivers() {
        Logger log = LoggerFactory.getLogger(SensorDriverRepository.class);
        for (ISensorDriver driver : publishedSensorDrivers)
            log.info("Found driver: " + driver.getId());

        // Create a scanner for all classes with the SensorDriverPublish annotation
        ClassPathScanningCandidateComponentProvider scanner;
        scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(SensorDriverPublish.class));
        Set<BeanDefinition> beanDefs = scanner
                .findCandidateComponents("it.univr.satella.drivers.implementation");

        // Save all beans ready for construction
        for (BeanDefinition bean : beanDefs) {
            try {

                String driverName = bean.getBeanClassName();
                Class<?> c = BeanGenerator.class.getClassLoader().loadClass(driverName);
                ISensorDriver driver = (ISensorDriver)c.getConstructor().newInstance();
                System.out.print(driver.getId());


            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {

                throw new RuntimeException(e);
            }
        }

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