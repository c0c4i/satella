package it.univr.satella.station.exceptions;

public class SensorByIdNotFoundException extends Exception {
    public SensorByIdNotFoundException() {
        super("Sensor ID not found in repository");
    }
}
