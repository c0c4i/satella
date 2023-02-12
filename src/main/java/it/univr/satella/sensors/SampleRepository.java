package it.univr.satella.sensors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {

    /**
     * Find all samples of a sensor in the specified range
     */

    List<Sample> findAllBySensorIdAndTimestampBetweenOrderByTimestamp(int sensorId, int timestampStart, int timestampEnd);
}
