package it.univr.satella;

import it.univr.satella.repository.SensorRepositoryTest;
import it.univr.satella.service.SensorServiceTest;
import it.univr.satella.service.SlotServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SensorRepositoryTest.class,
        SensorServiceTest.class,
        SlotServiceTest.class
})
public class SatellaTests {
}
