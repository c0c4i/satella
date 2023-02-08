package it.univr.satella.drivers;

import ch.qos.logback.core.joran.sanity.Pair;
import it.univr.satella.sensors.MeasureType;
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
    public Optional<Pair<MeasureType, Float>> measure() {
        return Optional.empty();
    }
}
