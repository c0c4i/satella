package it.univr.satella.alarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains all alarms to be processed
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> { }
