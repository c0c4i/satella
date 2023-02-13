package it.univr.satella.frontend.models;

public class MyData {
    private String label;
    private int value;

    public MyData(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }
}