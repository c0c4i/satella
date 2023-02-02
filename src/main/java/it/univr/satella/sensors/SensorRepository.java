package it.univr.satella.sensors;

import it.univr.satella.descriptors.SensorDescriptor;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
public class SensorRepository {

    /**
     * All known sensors types
     */
    private List<SensorDescriptor> sensorDescriptorList;

    /**
     * Load all sensors in sensors.json
     */
    public SensorRepository() {
        // TODO
    }

    /**
     *
     * @param name
     * @return
     */
    Optional<SensorDescriptor> findById(String name) {
        return sensorDescriptorList.stream()
                .filter(x -> x.getModel().equals(name))
                .findFirst();
    }
}
