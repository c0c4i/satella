package it.univr.satella.drivers;

public abstract class SensorDriver implements ISensorDriver {

    private String name;
    private String version;

    public SensorDriver(String name, String version) {
        this.name = name;
        this.version = version;
    }

    @Override
    public String getId() {
        return name + "[" + version + "]";
    }
}
