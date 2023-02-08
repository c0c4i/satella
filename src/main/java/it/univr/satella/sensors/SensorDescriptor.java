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
     * Constructs a new sensor descriptor
     */
    public SensorDescriptor(String model, String vendor, String driver, MeasureType measureUnit,
                            float minValue, float maxValue, float minVoltage, float maxVoltage,
                            float minAmperage, float maxAmperage)
    {
        this.model = model;
        this.vendor = vendor;
        this.driver = driver;
        this.measureUnit = measureUnit;
        this.minMeasureValue = minValue;
        this.maxMeasureValue = maxValue;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.minAmperage = minAmperage;
        this.maxAmperage = maxAmperage;
    }

    public SensorDescriptor() {
        this(null, null, null, null,
                0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Checks that the descriptor is coherent
     */
    public boolean isValid() {
        boolean voltageCheck = minVoltage <= maxVoltage;
        boolean amperageCheck = minAmperage <= maxAmperage;
        boolean valueCheck = minMeasureValue <= maxMeasureValue;
        boolean notNull = model != null && vendor != null && driver != null && measureUnit != null;
        return voltageCheck && amperageCheck && valueCheck && notNull;
    }

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
