package it.univr.satella.sensors;

import ch.qos.logback.core.joran.sanity.Pair;
import it.univr.satella.drivers.ISensorDriver;

import java.util.Optional;

public class SensorBundle {

    private final int id;
    private final SensorDescriptor descriptor;
    private final ISensorDriver driver;
    private final int slot;

    private float lastValue;
    private boolean enabled;

    public SensorDescriptor getDescriptor() {
        return descriptor;
    }

    public SensorBundle(int id, SensorDescriptor descriptor, ISensorDriver driver, int slot) {
        this.id = id;
        this.descriptor = descriptor;
        this.driver = driver;
        this.slot = slot;
        this.enabled = true;
        this.lastValue = 0.0f;
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

    public Optional<Float> measure() {
        if (enabled) return driver.measure();
        return Optional.empty();
    }

    public int getId() {
        return id;
    }

    public SensorDescriptor getDescriptor() {
        return descriptor;
    }

    public void setLastValue(float value) {
        this.lastValue = value;
    }

    public float getLastValue() {
        return lastValue;
    }

    public int getSlot() {
        return slot;
    }
}
