package it.univr.satella.sensors;

import it.univr.satella.comunication.ISatelliteCom;
import it.univr.satella.comunication.SatelliteCom;
import it.univr.satella.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class responsible for sending the measurements to the control center
 */
@Service
public class SampleSender {

    private final NotificationService notificationService;
    private final SampleRepository sampleRepository;
    private final ISatelliteCom satellite;

    /**
     * Maximum delay between successfully comunications
     * before notifying the user
     */
    private final static int MAXIMUM_DELAY_MIN = 30;

    /**
     * Currently accumulated delay
     */
    private int delayMin;

    private LocalDateTime lastTime;

    @Autowired
    public SampleSender(
            NotificationService notificationService,
            SampleRepository sampleRepository,
            ISatelliteCom satellite)
    {
        this.notificationService = notificationService;
        this.sampleRepository = sampleRepository;
        this.satellite = satellite;
        this.delayMin = 0;
        this.lastTime = LocalDateTime.now();
    }

    /**
     * Function that tries to send all samples to che control center
     * every minute
     */
    @Scheduled(fixedDelay = 1000)
    public void sendSamples() {
        LocalDateTime currTime = LocalDateTime.now();
        List<Sample> samples = sampleRepository.findAllByTimeBetweenOrderByTime(lastTime, currTime);
        if (!satellite.send(samples, "http://www.meteotrento.it/")) {
            delayMin += 1;
            if (delayMin >= MAXIMUM_DELAY_MIN)
                notificationService.warning("Unable to send data after " + MAXIMUM_DELAY_MIN + " mins");
        }
        else {
            lastTime = currTime;
            delayMin = 0;
        }
    }
}
