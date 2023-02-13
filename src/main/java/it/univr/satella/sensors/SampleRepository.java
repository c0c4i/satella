package it.univr.satella.sensors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {

    /**
     * Find all samples of a sensor in the specified range
     */
    List<Sample> findAllBySensorIdAndTimeBetweenOrderByTime(int sensorId, LocalDateTime timesStart, LocalDateTime timeEnd);

    /**
     * Find all samples in the specified range
     */
    List<Sample> findAllByTimeBetweenOrderByTime(LocalDateTime timesStart, LocalDateTime timeEnd);
}
