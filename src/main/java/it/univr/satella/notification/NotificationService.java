package it.univr.satella.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manages the notifications in the UI
 * and in the logger
 */
@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Create a info notification
     */
    public void info(String message) {
        notificationRepository.save(new Notification(NotificationType.Info, message));
        log.info(message);
    }

    /**
     * Create a warning notification
     */
    public void warning(String message) {
        notificationRepository.save(new Notification(NotificationType.Warning, message));
        log.warn(message);
    }

    /**
     * Create a error notification
     */
    public void error(String message) {
        notificationRepository.save(new Notification(NotificationType.Error, message));
        log.error(message);
    }

    /**
     * Returns all notifications and removes them from the repo
     */
    public List<Notification> extractAll() {
        List<Notification> result = notificationRepository.findAll();
        for (Notification notification : result)
            notificationRepository.delete(notification);
        return result;
    }
}
