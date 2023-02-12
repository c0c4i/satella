package it.univr.satella.sensors;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public int sensorId;

    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime time;

    public SampleUnit unit;
    public float measure;

    public Sample(int sensorId, LocalDateTime time, SampleUnit unit, float measure) {
        this.sensorId = sensorId;
        this.time = time;
        this.unit = unit;
        this.measure = measure;
    }
}
