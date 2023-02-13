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
        List<Alarm> alarms = alarmRepository.findByStatus(AlarmStatus.ToBeProcessed);
        if (!alarms.isEmpty()) {
            for (Alarm alarm : alarms) {
                if (satellite.send(alarm, "http://www.poliziaforestale.it/")) {
                    log.info("Successfully sent alarm " + alarm.id + " to control center");
                    alarm.setProcessed();
                    alarmRepository.save(alarm);
                } else {
                    log.info("Unable to send alarm " + alarm.id + " to control center");
                }
            }
        }
    }
}
