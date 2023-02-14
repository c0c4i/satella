package it.univr.satella.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Describes the capability of a sensor, that is its limits,
 */
@Entity
public class Sensor {

    @Id
    @JsonProperty("model")
    private String modelName;

    @JsonProperty("min_voltage")
    private float minVoltage;

    @JsonProperty("max_voltage")
    private float maxVoltage;

    @JsonProperty("min_amperage")
    private float minAmperage;

    @JsonProperty("max_amperage")
    private float maxAmperage;

    protected Sensor() { }

    /**
     * Constructs a new sensor descriptor
     */
    public Sensor(String modelName, float minVoltage, float maxVoltage, float minAmperage, float maxAmperage)
    {
        this.modelName = modelName;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.minAmperage = minAmperage;
        this.maxAmperage = maxAmperage;
    }

    /**
     * Checks that the descriptor is coherent
     */
    public boolean isValid() {
        boolean voltageCheck = minVoltage <= maxVoltage;
        boolean amperageCheck = minAmperage <= maxAmperage;
        boolean notNull = modelName != null;
        return voltageCheck && amperageCheck && notNull;
    }

    /**
     * Return true if the slot is compatible with this
     * sensor
     */
    public boolean isCompatible(SlotCapabilities capabilities) {
        boolean amperageCheck = minAmperage <= capabilities.amperage && capabilities.amperage <= maxAmperage;
        boolean voltageCheck  = minVoltage  <= capabilities.voltage  && capabilities.voltage  <= maxVoltage;
        return amperageCheck && voltageCheck;
    }

    /**
     * Model of the sensor, this is used as
     * the identifier.
     */
    public String getModelName() {
        return modelName;
    }

    public float getMinVoltage() {
        return minVoltage;
    }

    public float getMaxVoltage() {
        return maxVoltage;
    }

    public float getMinAmperage() {
        return minAmperage;
    }

    public float getMaxAmperage() {
        return maxAmperage;
    }
}
