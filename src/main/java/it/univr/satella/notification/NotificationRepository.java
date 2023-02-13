package it.univr.satella.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contains all notification to be displayed in the UI
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> { }
