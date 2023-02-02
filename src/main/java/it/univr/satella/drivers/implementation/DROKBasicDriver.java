package it.univr.satella.drivers.implementation;

import ch.qos.logback.core.joran.sanity.Pair;
import it.univr.satella.descriptors.MeasureType;
import it.univr.satella.descriptors.SensorDescriptor;
import it.univr.satella.drivers.AbstractSensorDriver;
import it.univr.satella.drivers.PublishedSensorDriver;

import java.util.Optional;

@PublishedSensorDriver
public class DROKBasicDriver extends AbstractSensorDriver {

    public DROKBasicDriver() {
        super("drok-driver", "0.0.1");
    }

    @Override
    public boolean isCompatible(SensorDescriptor descriptor) {
        return false;
    }

    @Override
    public void initialize(SensorDescriptor descriptor, int port) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public Optional<Pair<MeasureType, Float>> measure() {
        return Optional.empty();
    }
}
