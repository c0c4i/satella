package it.univr.satella.sensors;


import jakarta.persistence.*;

@Entity
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int sensorId;

    @Temporal(TemporalType.TIMESTAMP)
    public int timestamp;

    public SampleUnit unit;
    public float measure;

    public Sample(int sensorId, int timestamp, SampleUnit unit, float measure) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.unit = unit;
        this.measure = measure;
    }
}
