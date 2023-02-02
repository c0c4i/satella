package it.univr.satella.descriptors;

import java.util.List;

/**
 * Describes the capability of the station
 * and its slots
 */
public class StationDescriptor {

    private class SlotDescriptor {
        private float voltage;
        private float amperage;
    }

    private String version;
    private String model;
    private String vendor;

    private List<SlotDescriptor> slots;
}