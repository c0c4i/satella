package it.univr.satella.drivers;

public abstract class AbstractSensorDriver implements ISensorDriver {

    private String name;
    private String version;

    public AbstractSensorDriver(String name, String version) {
        this.name = name;
        this.version = version;
    }
}
