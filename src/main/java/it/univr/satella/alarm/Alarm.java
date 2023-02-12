package it.univr.satella.alarm;

import it.univr.satella.sensors.SensorDescriptor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents a violation in the measurements of a sensor
 */
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;

    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime time;

    public String sensorModel;
    public int sensorSlot;
    public float alarmValue;

    public Alarm(String sensorModel, int sensorSlot, float value, LocalDateTime time) {
        this.sensorModel = sensorModel;
        this.sensorSlot = sensorSlot;
        this.alarmValue = value;
        this.time = time;
    }

    public Alarm() {
        this(null, 0, 0.0f, null);
    }
}
