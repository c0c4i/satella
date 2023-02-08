package it.univr.satella.station.exceptions;

public class SensorNotCompatibleException extends Exception {
    public SensorNotCompatibleException(String sensorModel, int slot) {
        super("Sensor " + sensorModel + " is not compatible with slot " + slot);
    }
}
