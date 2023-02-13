package it.univr.satella;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SensorDriverRepositoryTest.class,
        SensorLoaderTest.class,
        SensorRepositoryTest.class,
        StationManagerTest.class,
        SensorDescriptorTest.class,
        AlarmTest.class,
        NotificationTest.class,
        SampleSenderTest.class,
})
public class SatellaApplicationTests { }