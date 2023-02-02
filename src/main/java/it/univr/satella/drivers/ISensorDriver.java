package it.univr.satella.drivers;

import ch.qos.logback.core.joran.sanity.Pair;
import it.univr.satella.descriptors.MeasureType;
import it.univr.satella.descriptors.SensorDescriptor;

import java.util.Optional;

/**
 * Interface representing a driver, that is an object responsible for
 * managing and sampling a sensor using its descriptor.
 */
public interface ISensorDriver {


    /**
     * All sensors must have an id of the form "name[version]"
     */
    String getId();

    /**
     * Check if a sensor can be managed by this driver
     */
    boolean isCompatible(SensorDescriptor descriptor);

    /**
     * Establishes a connection to the sensor hardware and begins
     * to manage it.
     */
    void initialize(SensorDescriptor descriptor, int port);

    /**
     * Closes the connection to the associated sensor.
     */
    void shutdown();

    /**
     * Samples the sensor, this can fail to return a measurement for any reason.
     * For example if there is a minimum delay between each measurement dictated
     * by the sensor's hardware.
     */
    Optional<Pair<MeasureType, Float>> measure();
}
