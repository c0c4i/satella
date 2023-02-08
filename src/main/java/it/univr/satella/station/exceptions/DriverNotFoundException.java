package it.univr.satella.station.exceptions;

public class DriverNotFoundException extends Exception {
    public DriverNotFoundException(String driverName) {
        super("Driver " + driverName + " not found in repository");
    }
}
