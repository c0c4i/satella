package it.univr.satella.descriptors;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

/**
 * Describes the structure and the properties of the station,
 */
public class StationDescriptor {

    /**
     * Describes a slot of the station, that is a dock where the user
     * can attach a sensor. It has a fixed voltage and amperage output.
     */
    public static class SlotDescriptor {

        /**
         * Output voltage of the slot
         */
        @JsonProperty
        public float voltage;

        /**
         * Output amperage of the slot
         */
        @JsonProperty
        public float amperage;
    }

    /**
     * Version of the station, in the standard form:
     * MAJOR.MINOR.PATCH
     */
    @JsonProperty
    private String version;

    /**
     * Model, or name, of the station.
     * It can have any form.
     */
    @JsonProperty
    private String model;

    /**
     * Vendor of the station.
     * It can have any form.
     */
    @JsonProperty
    private String vendor;

    /**
     * Available slot where to attach the sensors.
     */
    @JsonProperty
    private List<SlotDescriptor> slots;


    /**
     * Obtains the slot associated to the input index, if present.
     */
    public Optional<SlotDescriptor> getSlot(int index) {
        return Optional.ofNullable(slots.get(index));
    }
}