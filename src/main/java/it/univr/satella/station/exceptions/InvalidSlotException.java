package it.univr.satella.station.exceptions;

public class InvalidSlotException extends Exception {
    public InvalidSlotException(int slot) {
        super("Slot " + slot + " is not valid");
    }
}
