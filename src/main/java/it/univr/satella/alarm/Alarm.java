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

    /**
     * When the violation has occurred
     */
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime time;

    /**
     * Model of the sensor where the violation has occurred
     */
    public String sensorModel;

    /**
     * In what slot was the sensor attached to
     */
    public int sensorSlot;

    /**
     * Value of the sample that caused the violation
     */
    public float alarmValue;

    /**
     * Constructs a new Alarm object
     * @param sensorModel Model of the sensor where the violation has occurred
     * @param sensorSlot In what slot was the sensor attached to
     * @param value Value of the sample that caused the violation
     * @param time When the violation has occurred
     */
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
