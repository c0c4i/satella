package it.univr.satella.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.univr.satella.sensors.SensorDescriptor;

/**
 * Describes a slot of the station, that is a dock where the user
 * can attach a sensor. It has a fixed voltage and amperage output.
 */
public class SlotDescriptor {

    @JsonProperty
    private float voltage;
    @JsonProperty
    private float amperage;

    /**
     * Output voltage of the slot
     */
    public float getVoltage() {
        return voltage;
    }

    /**
     * Output amperage of the slot
     */
    public float getAmperage() {
        return amperage;
    }

    /**
     * Checks if this slot is compatible with the sensor
     * @param sensor Sensor descriptor to check
     */
    public boolean isCompatible(SensorDescriptor sensor) {
        boolean v = sensor.getMinVoltage()  <= voltage  && sensor.getMaxVoltage()  >= voltage;
        boolean a = sensor.getMinAmperage() <= amperage && sensor.getMaxAmperage() >= amperage;
        return v && a;
    }
}
