package it.univr.satella.sensors;

import ch.qos.logback.core.joran.sanity.Pair;
import it.univr.satella.drivers.ISensorDriver;

import java.util.Optional;

public class SensorBundle {

    private SensorDescriptor descriptor;
    private ISensorDriver driver;
    private int slot;

    private boolean enabled;

    public SensorBundle(SensorDescriptor descriptor, ISensorDriver driver, int slot) {
        this.descriptor = descriptor;
        this.driver = driver;
        this.enabled = true;
    }

    public void initialize() {
        driver.initialize(descriptor, slot);
    }

    public void shutdown() {
        driver.shutdown();
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    public Optional<Sample> measure() {
        if (enabled) return driver.measure();
        return Optional.empty();
    }
}