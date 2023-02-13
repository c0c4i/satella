package it.univr.satella;

import it.univr.satella.comunication.SatelliteCom;
import it.univr.satella.notification.NotificationRepository;
import it.univr.satella.notification.NotificationService;
import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SampleRepository;
import it.univr.satella.sensors.SampleSender;
import it.univr.satella.sensors.SensorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SampleRepository.class, NotificationService.class, NotificationRepository.class })
@EnableAutoConfiguration
public class SampleSenderTest {

    @Autowired private NotificationService notificationService;
    @Autowired private SampleRepository sampleRepository;

    @Test
    public void testSender() {

        final int MAXIMUM_DELAY_MIN = 30;

        // Create sender with an always failing satellite connection
        SampleSender sampleSender = new SampleSender(notificationService, sampleRepository,
                ((obj, url) -> false));

        // Tries to send data 9 times, we should not have a notification
        for (int i = 0; i < MAXIMUM_DELAY_MIN - 1; i++)
            sampleSender.sendSamples();
        assertTrue(notificationService.extractAll().isEmpty());

        // We have exceded the maximum delay, we should have a notification
        sampleSender.sendSamples();
        assertFalse(notificationService.extractAll().isEmpty());
    }
}
