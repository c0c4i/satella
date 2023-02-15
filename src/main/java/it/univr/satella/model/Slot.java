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

    public int getSlot() {
        return slot;
    }

    public SlotCapabilities getCapabilities() {
        return slotCapabilities;
    }

    public boolean attachSensor(Sensor sensor) {
        if (sensor.isCompatible(slotCapabilities) == -1) {
            attachedSensor = sensor;
            return true;
        }
        return false;
    }

    public boolean hasAttachedSensor() {
        return attachedSensor != null;
    }

    public Sensor getAttachedSensor() {
        return attachedSensor;
    }

    public void detachSensor() {
        attachedSensor = null;
    }
}
