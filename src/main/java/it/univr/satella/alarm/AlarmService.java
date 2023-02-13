package it.univr.satella.alarm;

import it.univr.satella.comunication.ISatelliteCom;
import it.univr.satella.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending all alarms
 */
@Service
public class AlarmService {

    private final NotificationService notificationService;
    private final ISatelliteCom satellite;

    @Autowired
    public AlarmService(NotificationService notificationService, ISatelliteCom satellite) {
        this.notificationService = notificationService;
        this.satellite = satellite;

    }

    /**
     * Send an alarm to the control center, doesn't return until the request is successful
     */
    public void sendAlarm(Alarm alarm) {
        notificationService.warning("Value of sensor " + alarm.sensorModel + " is outside safe range: " + alarm.alarmValue);
        while (!satellite.send(alarm, "http://www.poliziaforestale.it/")) {
            notificationService.error("Unable to send alarm!");
        }
    }
}
