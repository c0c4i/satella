package it.univr.satella.descriptors;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes the capability of a sensor, that is its limits,
 * unit of measure and the necessary driver.
 */
//@Entity
public class SensorDescriptor {

    /**
     * Model of the sensor, this is used as
     * the identifier.
     */
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

    @JsonProperty("sample_delay_sec")
    private float sampleDelaySec;

    @JsonProperty
    private String driver;

    public String getModel() {
        return model;
    }
}
