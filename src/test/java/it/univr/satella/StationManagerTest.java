package it.univr.satella;

import it.univr.satella.comunication.SatelliteCom;
import it.univr.satella.drivers.ISensorDriver;
import it.univr.satella.drivers.SensorDriver;
import it.univr.satella.drivers.SensorDriverRepository;
import it.univr.satella.notification.NotificationRepository;
import it.univr.satella.notification.NotificationService;
import it.univr.satella.sensors.*;
import it.univr.satella.station.SlotDescriptor;
import it.univr.satella.station.StationManager;
import it.univr.satella.station.exceptions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the functionality and integration of the StationManager
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        SensorRepository.class,
        NotificationRepository.class,
        NotificationService.class,
        SatelliteCom.class
})
@EnableAutoConfiguration
public class StationManagerTest {

    private final static String STATION_CONFIG_FILE = "src/test/resources/station.json";

    @Autowired private NotificationService notificationService;
    @Autowired private SensorRepository sensorRepository;
    @Autowired private SampleRepository sampleRepository;

    private StationManager station;

    // Mock driver always compatible
    private static class CompatibleDriver extends SensorDriver {
        public CompatibleDriver() {
            super("driver-compatible", "0.0.1");
        }

        @Override
        public boolean isCompatible(SensorDescriptor descriptor) {
            return true;
        }

        @Override
        public ISensorDriver copy() {
            return new CompatibleDriver();
        }
    }

    // Mock driver always not compatible
    private static class NotCompatibleDriver extends SensorDriver {
        public NotCompatibleDriver() {
            super("driver-not-compatible", "0.0.1");
        }

        @Override
        public boolean isCompatible(SensorDescriptor descriptor) {
            return false;
        }

        @Override
        public ISensorDriver copy() {
            return new NotCompatibleDriver();
        }
    }

    @Before
    public void initialize()  {

        // Load all sensors
        SensorLoader sensorLoader = new SensorLoader(sensorRepository);
        sensorLoader.loadSensorsAt("src/test/resources/station_sensors.json");

        // Create fake driver for this test
        SensorDriverRepository sensorDriverRepository = new SensorDriverRepository(List.of(
                new CompatibleDriver(), new NotCompatibleDriver()
        ));

        station = new StationManager(STATION_CONFIG_FILE,
                notificationService, sensorDriverRepository, sensorRepository, sampleRepository);
    }

    @Test
    public void testSlotDeserialization() {
        List<SlotDescriptor> slotDescriptorList = station.getSlotDescriptors();
        assertEquals(2, slotDescriptorList.size());

        SlotDescriptor s1 = slotDescriptorList.get(0);
        assertEquals(5.0, s1.getVoltage(), 0.0);
        assertEquals(2.0, s1.getAmperage(), 0.0);

        SlotDescriptor s2 = slotDescriptorList.get(1);
        assertEquals(3.0, s2.getVoltage(), 0.0);
        assertEquals(2.0, s2.getAmperage(), 0.0);
    }

    @Test
    public void testAttach() throws Exception {
        station.loadConfigurationAt("src/test/resources/configurations/correct.json");
    }

    @Test(expected = SensorNotFoundException.class)
    public void testSensorNotFound() throws Exception {
        station.loadConfigurationAt("src/test/resources/configurations/sensor_not_found.json");
    }

    @Test(expected = InvalidSlotException.class)
    public void testInvalidSlot() throws Exception {
        station.loadConfigurationAt("src/test/resources/configurations/invalid_slot.json");
    }

    @Test(expected = DriverNotFoundException.class)
    public void testDriverNotFound() throws Exception {
        station.loadConfigurationAt("src/test/resources/configurations/driver_not_found.json");
    }

    @Test(expected = SensorNotCompatibleException.class)
    public void testSensorNotCompatible() throws Exception {
        station.loadConfigurationAt("src/test/resources/configurations/sensor_not_compatible.json");
    }

    @Test(expected = DriverNotCompatibleException.class)
    public void testDriverNotCompatible() throws Exception {
        station.loadConfigurationAt("src/test/resources/configurations/driver_not_compatible.json");
    }
}
