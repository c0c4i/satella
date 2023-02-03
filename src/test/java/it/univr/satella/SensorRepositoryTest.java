package it.univr.satella;

import it.univr.satella.sensors.SensorDescriptor;
import it.univr.satella.sensors.SensorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootTest
public class SensorRepositoryTest {



    @Test
    public void correctLoadFromFile() {
        SensorRepository repo = new SensorRepository("src/test/resources/sensors.json");
        List<SensorDescriptor> values = repo.getAll();



    }

}
