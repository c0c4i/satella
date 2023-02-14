package it.univr.satella.model;

public class Slot {

    private int slot;

    /**
     * Describes the capabilities of the slot
     */
    private SlotCapabilities slotCapabilities;

    /**
     * Currently attached sensor
     */
    private Sensor attachedSensor;

    protected Slot() { }

    public Slot(int slot, SlotCapabilities slotCapabilities) {
        this.slotCapabilities = slotCapabilities;
        this.attachedSensor = null;
        this.slot = slot;
    }

    public SlotCapabilities getCapabilities() {
        return slotCapabilities;
    }

    public void attachSensor(Sensor sensor) {
        attachedSensor = sensor;
    }

    public boolean hasAttachedSensor() {
        return attachedSensor != null;
    }

    public void detachSensor() {
        attachedSensor = null;
    }
}
