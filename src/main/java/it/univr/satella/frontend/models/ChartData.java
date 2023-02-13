package it.univr.satella.frontend.models;


import it.univr.satella.sensors.SensorBundle;

import java.util.List;

public class ChartData {
    private SensorBundle sensor;
    private List<Integer> labels;
    private List<Float> values;


    public ChartData(SensorBundle model, List labels, List values) {
        this.sensor = model;
        this.labels = labels;
        this.values = values;
    }

    public SensorBundle getSensor() {
        return sensor;
    }
    public void setSensor(SensorBundle sensor) {
        this.sensor = sensor;
    }
    public List getLabels() {
        return labels;
    }
    public void setLabels(List labels) {
        this.labels = labels;
    }
    public List getValues() {
        return values;
    }
    public void setValues(List values) {
        this.values = values;
    }

}