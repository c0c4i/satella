package it.univr.satella.controllers;

import it.univr.satella.model.Sensor;
import it.univr.satella.model.Slot;
import it.univr.satella.service.SensorService;
import it.univr.satella.service.SlotService;
import it.univr.satella.station.exceptions.SensorByIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class AppController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SlotService slotService;

    @RequestMapping("/slots")
    public String slots(Model model){
        model.addAttribute("slots", slotService.getSlots());
        return "slots/index";
    }

    @RequestMapping("/sensors")
    public String sensors(Model model){
        List<Sensor> sensors = sensorService.findAll();
        if(sensors.size() == 0) {
            return "sensors/empty";
        }

        model.addAttribute("sensors", sensorService.findAll());
        return "sensors/index";
    }

    @RequestMapping("/")
    public RedirectView redirectSlots(){
        return new RedirectView("slots");
    }

    @RequestMapping("/sensors/{id}")
    public String sensorDetails(@PathVariable("id") int id, Model model) {
        return "sensors/_id";
//        try {
//            SensorBundle sensorBundle = stationManager.getSensorById(id);
//            model.addAttribute("id", id);
//            return "sensor_details";
//        } catch (SensorByIdNotFoundException e) {
//            return "sensor_not_found";
//        }
    }
}