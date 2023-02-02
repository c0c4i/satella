package it.univr.satella.descriptors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Describes the capability of the station
 * and its slots
 */
public class StationDescriptor {

    private class Slot {
        @JsonProperty
        private float voltage;
        @JsonProperty
        private float amperage;
    }

    @JsonProperty
    private String version;
    @JsonProperty
    private String model;
    @JsonProperty
    private String vendor;
    @JsonProperty
    private List<Slot> slots;
}