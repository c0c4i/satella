package it.univr.satella.sensors;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes the capability of a sensor, that is its limits,
 * unit of measure and the necessary driver.
 */
public class SensorDescriptor {

    @JsonProperty
    private String model;
    @JsonProperty
    private String vendor;
    @JsonProperty("measure_unit")
    private MeasureType measureUnit;
    @JsonProperty("min_value")
    private float minMeasureValue;
    @JsonProperty("max_value")
    private float maxMeasureValue;
    @JsonProperty("min_voltage")
    private float minVoltage;
    @JsonProperty("max_voltage")
    private float maxVoltage;
    @JsonProperty("min_amperage")
    private float minAmperage;
    @JsonProperty("max_amperage")
    private float maxAmperage;
    @JsonProperty
    private String driver;

    /**
     * Model of the sensor, this is used as
     * the identifier.
     */
    public String getModel() {
        return model;
    }

    public MeasureType getMeasureUnit() {
        return measureUnit;
    }

    public float getMinMeasureValue() {
        return minMeasureValue;
    }

    public float getMaxMeasureValue() {
        return maxMeasureValue;
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

    public String getDriver() {
        return driver;
    }

    public String getVendor() {
        return vendor;
    }
}
