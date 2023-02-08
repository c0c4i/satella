package it.univr.satella;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SensorDriverRepositoryTest.class,
        SensorRepositoryTest.class,
        StationManagerTest.class
})
public class SatellaApplicationTests { }
