package it.univr.satella.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a notification in the UI
 */
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    public NotificationType type;
    public String message;

    public Notification(NotificationType type, String message) {
        this.type = type;
        this.message = message;
    }

    public Notification() {
        this(NotificationType.Error, "none");
    }
}
