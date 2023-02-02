package it.univr.satella.drivers;

import ch.qos.logback.core.joran.sanity.Pair;
import it.univr.satella.descriptors.MeasureType;
import it.univr.satella.descriptors.SensorDescriptor;

import java.util.Optional;

public interface ISensorDriver {

    /**
     *
     * @param descriptor
     * @return
     */
    boolean isCompatible(SensorDescriptor descriptor);

    /**
     *
     * @param descriptor
     */
    void initialize(SensorDescriptor descriptor, int port);

    /**
     *
     */
    void shutdown();

    /**
     *
     * @return
     */
    Optional<Pair<MeasureType, Float>> measure();
}
