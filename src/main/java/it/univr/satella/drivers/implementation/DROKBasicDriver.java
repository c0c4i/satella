package it.univr.satella.drivers.implementation;

import ch.qos.logback.core.joran.sanity.Pair;
import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SampleUnit;
import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.drivers.ISensorDriver;
import it.univr.satella.drivers.SensorDriver;
import it.univr.satella.drivers.SensorDriverPublish;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@SensorDriverPublish
public class DROKBasicDriver extends SensorDriver {

    public DROKBasicDriver() {
        super("drok-driver", "0.0.1");
    }

    @Override
    public ISensorDriver copy() {
        return new DROKBasicDriver();
    }

    @Override
    public boolean isCompatible(SensorDescriptor descriptor) {
        return descriptor.getVendor().equalsIgnoreCase("drok");
    }

    @Override
    public void initialize(SensorDescriptor descriptor, int port) {
        // FAKE
    }

    @Override
    public void shutdown() {
        // FAKE
    }

    @Override
    public Optional<Float> measure() {
        return Optional.empty();
    }
}
