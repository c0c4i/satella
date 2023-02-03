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
    private List<SlotDescriptor> slots;

    /**
     * Obtains the slot associated to the input index, if present.
     */
    public List<SlotDescriptor> getSlots() {
        return this.slots;
    }
}