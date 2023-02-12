package it.univr.satella.alarm;

import it.univr.satella.comunication.ISatelliteCom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Component responsible for sending alarms to the control center
 */
@Service
public class AlarmSender {

    private final static Logger log = LoggerFactory.getLogger(AlarmSender.class);
    private final AlarmRepository alarmRepository;
    private final ISatelliteCom satellite;

    @Autowired
    public AlarmSender(
            AlarmRepository alarmRepository,
            ISatelliteCom satellite)
    {
        this.alarmRepository = alarmRepository;
        this.satellite = satellite;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void sendAlarms() {
        List<Alarm> alarms = alarmRepository.findAll();
        if (satellite.send(alarms, "http://www.meteotrento.it/")) {
            log.info("Successfully sent alarms to control center");
            alarmRepository.deleteAll();
        }
        else {
            log.info("Unable to send alarms to control center");
        }
    }
}
