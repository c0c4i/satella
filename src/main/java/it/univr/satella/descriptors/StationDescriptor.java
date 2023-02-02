package it.univr.satella.descriptors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

/**
 * Describes the capability of the station
 * and its slots
 */
public class StationDescriptor {

    public static class SlotDescriptor {
        @JsonProperty
        public float voltage;
        @JsonProperty
        public float amperage;
    }

    @JsonProperty
    private String version;
    @JsonProperty
    private String model;
    @JsonProperty
    private String vendor;
    @JsonProperty
    private List<SlotDescriptor> slots;

    public Optional<SlotDescriptor> getSlot(int index) {
        return Optional.ofNullable(slots.get(index));
    }
}