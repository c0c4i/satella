package it.univr.satella.station.exceptions;

public class DriverNotCompatibleException extends Exception {
    public DriverNotCompatibleException(String sensorModel, String driverName) {
        super("Driver " + driverName + " is not compatible with sensor " + sensorModel);
    }
}
