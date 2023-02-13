package it.univr.satella.frontend;

import it.univr.satella.sensors.SensorBundle;
import it.univr.satella.station.StationManager;
import it.univr.satella.station.exceptions.SensorByIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class AppController {

    @Autowired
    private StationManager stationManager;

    @RequestMapping("/sensors")
    public String list(Model model){
        List<SensorBundle> sensors = stationManager.getSensors();
        model.addAttribute("sensors", sensors);
        return "sensors";
    }

    @RequestMapping("/sensors/{id}")
    public String sensorDetails(@PathVariable("id") int id, Model model) {
        try {
            SensorBundle sensorBundle = stationManager.getSensorById(id);
            model.addAttribute("id", id);
            return "sensor_details";
        } catch (SensorByIdNotFoundException e) {
            return "sensor_not_found";
        }
    }
}