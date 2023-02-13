package it.univr.satella.sensors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<SensorDescriptor, String> {

    /**
     * Obtains a sensor descriptor by its model
     */
    SensorDescriptor findByModel(String model);

    /**
     * Obtains all sensor descriptors
     */
    List<SensorDescriptor> findAll();
}
