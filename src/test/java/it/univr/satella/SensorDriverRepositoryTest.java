package it.univr.satella;

import it.univr.satella.drivers.ISensorDriver;
import it.univr.satella.drivers.SensorDriver;
import it.univr.satella.drivers.SensorDriverRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class SensorDriverRepositoryTest {

    private static SensorDriverRepository repository;

    @BeforeClass
    public static void initialize() {
        repository = new SensorDriverRepository(new ArrayList<>(List.of(
                new SensorDriver("sensor", "0.4.5") {}
        )));
    }

    @Test
    public void testGetDriver() {
        assertTrue(repository.getDriver("sensor[0.4.5]").isPresent());
        assertTrue(repository.getDriver("sensor[0.0.5]").isEmpty());
        assertTrue(repository.getDriver("abc").isEmpty());
    }

    @Test
    public void testSave() {
        repository.save(new SensorDriver("driver", "0.0.1") { });
        Optional<ISensorDriver> driver = repository.getDriver("driver[0.0.1]");
        assertTrue(driver.isPresent());
    }
}
