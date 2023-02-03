package it.univr.satella.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.univr.satella.station.SlotDescriptor;

import java.util.List;
import java.util.Optional;

/**
 * Describes the structure and the properties of the station,
 */
public class StationDescriptor {

    @JsonProperty
    private String version;
    @JsonProperty
    private String model;
    @JsonProperty
    private String vendor;
    @JsonProperty
    private List<SlotDescriptor> slots;

    /**
     * Version of the station, in the standard form:
     * MAJOR.MINOR.PATCH
     */
    public String getVersion() {
        return version;
    }

    /**
     * Model, or name, of the station.
     * It can have any form.
     */
    public String getModel() {
        return model;
    }

    /**
     * Vendor of the station.
     * It can have any form.
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Obtains the slot associated to the input index, if present.
     */
    public List<SlotDescriptor> getSlots() {
        return this.slots;
    }
}