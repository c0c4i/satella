package it.univr.satella;

import it.univr.satella.alarm.AlarmRepository;
import it.univr.satella.drivers.SensorDriver;
import it.univr.satella.drivers.SensorDriverRepository;
import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.sensors.SensorLoader;
import it.univr.satella.sensors.SensorRepository;
import it.univr.satella.station.SlotDescriptor;
import it.univr.satella.station.StationDescriptor;
import it.univr.satella.station.StationManager;
import it.univr.satella.station.exceptions.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the functionality and integration of the StationManager
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SensorRepository.class, AlarmRepository.class })
@EnableAutoConfiguration
public class StationManagerTest {

    private final static String STATION_CONFIG_FILE = "src/test/resources/station.json";

    @Autowired private SensorRepository sensorRepository;
    @Autowired private AlarmRepository alarmRepository;

    private StationManager station;

    @Before
    public void initialize() throws IOException  {

        // Load all sensors
        SensorLoader sensorLoader = new SensorLoader(sensorRepository);
        sensorLoader.loadSensorsAt("src/test/resources/station_sensors.json");

        // Create fake driver for this test
        SensorDriverRepository sensorDriverRepository = new SensorDriverRepository(List.of(
                new SensorDriver("driver-not-compatible", "0.0.1") {
                    @Override
                    public boolean isCompatible(SensorDescriptor descriptor) {
                        return false;
                    }
                },
                new SensorDriver("driver-compatible", "0.0.1") {
                    @Override
                    public boolean isCompatible(SensorDescriptor descriptor) {
                        return true;
                    }
                }
        ));
        sensorDriverRepository.printSensorDrivers();
        station = new StationManager(STATION_CONFIG_FILE,
                sensorDriverRepository, sensorRepository, null, alarmRepository);
    }

    @Test
    public void testDeserialization() {
        StationDescriptor descriptor = station.getDescriptor();
        assertEquals(2, descriptor.getSlots().size());

        SlotDescriptor s1 = descriptor.getSlots().get(0);
        assertEquals(5.0, s1.getVoltage(), 0.0);
        assertEquals(2.0, s1.getAmperage(), 0.0);

        SlotDescriptor s2 = descriptor.getSlots().get(1);
        assertEquals(3.0, s2.getVoltage(), 0.0);
        assertEquals(2.0, s2.getAmperage(), 0.0);
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
