package it.univr.satella;

import it.univr.satella.alarm.Alarm;
import it.univr.satella.alarm.AlarmRepository;
import it.univr.satella.alarm.AlarmSender;
import it.univr.satella.alarm.AlarmStatus;
import it.univr.satella.comunication.ISatelliteCom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AlarmRepository.class })
@EnableAutoConfiguration
public class AlarmTest {

    @Autowired
    private AlarmRepository alarmRepository;

    @Test
    public void testComSuccess() {

        alarmRepository.deleteAll();
        alarmRepository.save(new Alarm("sensor-1", 0, 15.0f, LocalDateTime.now()));
        AlarmSender sender = new AlarmSender(alarmRepository, (obj, url) -> true);
        sender.sendAlarms();

        List<Alarm> alarmsToBeProcessed = alarmRepository.findByStatus(AlarmStatus.ToBeProcessed);
        assertTrue(alarmsToBeProcessed.isEmpty());

        List<Alarm> alarmsProcessed = alarmRepository.findByStatus(AlarmStatus.Processed);
        assertFalse(alarmsProcessed.isEmpty());
    }

    @Test
    public void testComFailure() {

        alarmRepository.deleteAll();
        alarmRepository.save(new Alarm("sensor-1", 0, 15.0f, LocalDateTime.now()));
        AlarmSender sender = new AlarmSender(alarmRepository, (obj, url) -> false);
        sender.sendAlarms();

        List<Alarm> alarmsToBeProcessed = alarmRepository.findByStatus(AlarmStatus.ToBeProcessed);
        assertFalse(alarmsToBeProcessed.isEmpty());

        List<Alarm> alarmsProcessed = alarmRepository.findByStatus(AlarmStatus.Processed);
        assertTrue(alarmsProcessed.isEmpty());
    }
}
