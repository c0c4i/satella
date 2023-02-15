package it.univr.satella.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Describes the capability of a sensor, that is its limits,
 */
@Entity
public class Sensor {

    @Id
    @JsonProperty("model")
    private String modelName;

    @JsonProperty("min_voltage")
    private float minVoltage;

    @JsonProperty("max_voltage")
    private float maxVoltage;

    @JsonProperty("min_amperage")
    private float minAmperage;

    @JsonProperty("max_amperage")
    private float maxAmperage;

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setMinVoltage(float minVoltage) {
        this.minVoltage = minVoltage;
    }

    public void setMaxVoltage(float maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public void setMinAmperage(float minAmperage) {
        this.minAmperage = minAmperage;
    }

    public void setMaxAmperage(float maxAmperage) {
        this.maxAmperage = maxAmperage;
    }

    public Sensor() { }

    /**
     * Constructs a new sensor descriptor
     */
    public Sensor(String modelName, float minVoltage, float maxVoltage, float minAmperage, float maxAmperage)
    {
        this.modelName = modelName;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.minAmperage = minAmperage;
        this.maxAmperage = maxAmperage;
    }

    /**
     * Checks that the descriptor is coherent
     */
    public int isValid() {
        if(modelName == null) return 1;
        if(!checkStringWithoutSpaces(modelName)) return 2;
        if(minVoltage > maxVoltage) return 3;
        if(minAmperage > maxAmperage) return 4;
        return -1;
    }

    /**
     * Return true if the slot is compatible with this
     * sensor
     */
    public int isCompatible(SlotCapabilities capabilities) {
        if(minAmperage > capabilities.amperage) return 5;
        if(maxAmperage < capabilities.amperage) return 6;
        if(minVoltage > capabilities.voltage) return 7;
        if(maxVoltage < capabilities.voltage) return 8;
        return -1;
    }

    public String getInvalidMessage(int error) {
        switch (error) {
            case 1:
                return "Il modello non può essere vuoto!";
            case 2:
                return "Il modello non può contenere spazi vuoti!";
            case 3:
                return "Il campo \"Amperaggio minimo\" deve essere inferiore al campo \"Amperaggio massimo\" ";
            case 4:
                return "Il \"Voltaggio minimo\" deve essere inferiore al \"Voltaggio massimo\" ";
        }
        return null;
    }

    public String getIncompatibleMessage(int error, SlotCapabilities capabilities) {
        switch (error) {
            case 5:
                return "Il campo \"Amperaggio minimo\" deve inferiore o uguale alla capacità dello slot a cui è collegato (" + capabilities.amperage + "A)";
            case 6:
                return "Il campo \"Amperaggio massimo\" deve superiore o uguale alla capacità dello slot a cui è collegato (" + capabilities.amperage + "A)";
            case 7:
                return "Il \"Voltaggio minimo\" deve inferiore o uguale alla capacità dello slot a cui è collegato (" + capabilities.voltage + "V)";
            case 8:
                return "Il \"Voltaggio massimo\" deve superiore o uguale alla capacità dello slot a cui è collegato (" + capabilities.voltage + "V)";
        }
        return null;
    }

    private static boolean checkStringWithoutSpaces(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Model of the sensor, this is used as
     * the identifier.
     */
    public String getModelName() {
        return modelName;
    }

    public float getMinVoltage() {
        return minVoltage;
    }

    public float getMaxVoltage() {
        return maxVoltage;
    }

    public float getMinAmperage() {
        return minAmperage;
    }

    public float getMaxAmperage() {
        return maxAmperage;
    }
}
