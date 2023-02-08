package it.univr.satella.station.exceptions;

public class SensorNotFoundException extends Exception {
    public SensorNotFoundException(String sensorModel) {
        super("Sensor " + sensorModel + " not found in repository");
    }
}
