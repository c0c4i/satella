package it.univr.satella.descriptors;

/**
 * Describes the capability of a sensor, its limits and
 * the necessary driver
 */
public class SensorDescriptor {

    private String model;
    private String vendor;
    private MeasureType measureUnit;

    private float minMeasureValue;
    private float maxMeasureValue;

    private float minVoltage;
    private float maxVoltage;
    private float minAmperage;
    private float maxAmperage;

    private float sampleDelaySec;

    private String driver;
}
