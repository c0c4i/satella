package it.univr.satella.sensors;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;

/**
 * All supported types of unit of measurement of the sensors
 */
public enum SampleUnit {
    Temperature,
    Pressure,
    WindSpeed,
    Humidity;

    private static HashMap<String, SampleUnit> namesMap = new HashMap<>();
    static {
        namesMap.put("temperature", SampleUnit.Temperature);
        namesMap.put("pressure",    SampleUnit.Pressure);
        namesMap.put("windspeed",   SampleUnit.WindSpeed);
        namesMap.put("humidity",    SampleUnit.Humidity);
    }

    /**
     * Describes how to load a measure from a string, this is more
     * flexible than the standard method as is allows combinations
     * of uppercase and lowercase values.
     */
    @JsonCreator
    public static SampleUnit forValue(String value) throws RuntimeException {
        if (namesMap.containsKey(value.toLowerCase()))
            return namesMap.get(value.toLowerCase());
        throw new RuntimeException("Unable to parse measure unity");
    }
}
