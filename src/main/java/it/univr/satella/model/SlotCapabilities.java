package it.univr.satella.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SlotCapabilities {

    @JsonProperty("amperage")
    public float amperage;

    @JsonProperty("voltage")
    public float voltage;

    protected SlotCapabilities () { }

    public SlotCapabilities(float amperage, float voltage) {
        this.amperage = amperage;
        this.voltage = voltage;
    }
}
