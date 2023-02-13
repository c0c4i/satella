package it.univr.satella;

import it.univr.satella.notification.Notification;
import it.univr.satella.notification.NotificationRepository;
import it.univr.satella.notification.NotificationService;
import it.univr.satella.notification.NotificationType;
import it.univr.satella.sensors.SensorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NotificationService.class, NotificationRepository.class })
@EnableAutoConfiguration
public class NotificationTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    public void testNotification() {

        notificationService.info("info");
        notificationService.warning("warning");
        notificationService.error("error");

        List<Notification> notifications = notificationService.extractAll();
        assertEquals(3, notifications.size());

        assertTrue(notifications.stream().anyMatch(x -> x.type.equals(NotificationType.Info)));
        assertTrue(notifications.stream().anyMatch(x -> x.type.equals(NotificationType.Warning)));
        assertTrue(notifications.stream().anyMatch(x -> x.type.equals(NotificationType.Error)));

        notifications = notificationService.extractAll();
        assertTrue(notifications.isEmpty());
    }
}
