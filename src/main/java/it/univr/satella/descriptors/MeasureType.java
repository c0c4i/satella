package it.univr.satella.descriptors;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;

/**
 * All supported types of unit of measurement of the sensors
 */
public enum MeasureType {
    Temperature,
    Pressure,
    WindSpeed,
    Humidity;

    private static HashMap<String, MeasureType> namesMap = new HashMap<>();
    static {
        namesMap.put("temperature", MeasureType.Temperature);
        namesMap.put("pressure",    MeasureType.Pressure);
        namesMap.put("windspeed",   MeasureType.WindSpeed);
        namesMap.put("humidity",    MeasureType.Humidity);
    }

    /**
     * Describes how to load a measure from a string, this is more
     * flexible than the standard method as is allows combinations
     * of uppercase and lowercase values.
     */
    @JsonCreator
    public static MeasureType forValue(String value) {
        return namesMap.get(value.toLowerCase());
    }
}