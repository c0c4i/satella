package it.univr.satella.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlotCapabilities {

    @JsonProperty
    public float amperage;

    @JsonProperty
    public float voltage;

    public SlotCapabilities(float amperage, float voltage) {
        this.amperage = amperage;
        this.voltage = voltage;
    }
}
