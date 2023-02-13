package it.univr.satella.drivers;

import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SensorDescriptor;

import java.util.Optional;

public abstract class SensorDriver implements ISensorDriver {

    private final String name;
    private final String version;

    protected SensorDriver(String name, String version) {
        this.name = name;
        this.version = version;
    }

    @Override
    public String getId() {
        return name + "[" + version + "]";
    }

    @Override
    public ISensorDriver copy() {
        return new SensorDriver(name, version) { };
    }

    @Override
    public boolean isCompatible(SensorDescriptor descriptor) {
        return false;
    }

    @Override
    public void initialize(SensorDescriptor descriptor, int port) { }

    @Override
    public void shutdown() { }

    @Override
    public Optional<Float> measure() {
        return Optional.empty();
    }
}
