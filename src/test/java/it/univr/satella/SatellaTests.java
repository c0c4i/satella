package it.univr.satella;

import it.univr.satella.model.SensorTest;
import it.univr.satella.service.SensorServiceTest;
import it.univr.satella.service.SlotServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SensorServiceTest.class,
        SlotServiceTest.class,
        SensorTest.class
})
public class SatellaTests {
}
