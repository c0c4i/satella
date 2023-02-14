package it.univr.satella.repository;

import it.univr.satella.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {

    /**
     * Obtains a sensor descriptor by its model
     */
    Sensor findByModelName(String modelName);

    /**
     * Obtains all sensor descriptors
     */
    List<Sensor> findAll();
}
