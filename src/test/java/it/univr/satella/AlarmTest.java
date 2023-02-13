package it.univr.satella;

import it.univr.satella.alarm.Alarm;
import it.univr.satella.alarm.AlarmService;
import it.univr.satella.notification.NotificationRepository;
import it.univr.satella.notification.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NotificationService.class, NotificationRepository.class })
@EnableAutoConfiguration
public class AlarmTest {

    @Autowired private NotificationService notificationService;

    @Test
    public void testAlarm() {

        // Create an alarm service with a working satellite com link
        AlarmService alarmService = new AlarmService(notificationService, ((obj, url) -> true));
        alarmService.sendAlarm(new Alarm("sensor-1", 0, 15.0f, LocalDateTime.now()));

        // We should have a notification
        assertFalse(notificationService.extractAll().isEmpty());
    }
}
