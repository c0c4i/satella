package it.univr.satella.controllers;

import it.univr.satella.model.Sensor;
import it.univr.satella.model.Slot;
import it.univr.satella.service.SensorService;
import it.univr.satella.service.SlotService;
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
        List<Slot> slots = slotService.getSlots();
        if(slots.size() == 0) {
            return "slots/empty";
        }
        model.addAttribute("slots", slots );
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
}