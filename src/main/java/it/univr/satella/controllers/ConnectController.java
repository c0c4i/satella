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
public class ConnectController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SlotService slotService;

    @RequestMapping("/slots/{id}/connect")
    public String connectSensor(@PathVariable("id") int id, Model model) {
        Slot slot = slotService.getSlotByPort(id);
        if(slot == null) {
            return "slots/_id/not-found";
        }

        List<Sensor> sensors = sensorService.findAllCompatible(slot.getCapabilities());
        if(sensors.size() == 0) {
            return "slots/_id/sensors-empty";
        }

        model.addAttribute("sensors", sensors);
        model.addAttribute("slot", slot.getSlot());
        return "slots/_id/connect";
    }

    @RequestMapping("/slots/{id}/connect/{model}")
    public RedirectView connectSensor(@PathVariable("id") int id, @PathVariable("model") String modelName, RedirectAttributes attributes) {
        Slot slot = slotService.getSlotByPort(id);
        if(slot == null) {
            // Redirect to /slots with error=1
            attributes.addAttribute("error", 1);
            return new RedirectView("/slots");
        }

        Sensor sensor = sensorService.findSensorByModelName(modelName);
        if(sensor == null) {
            // Redirect to /slots with error=2
            attributes.addAttribute("error", 2);
            return new RedirectView("/slots");
        }

        boolean result = slotService.attachSensorToSlot(id, sensor);

        if(result) {
            attributes.addAttribute("success", 1);
        } else {
            attributes.addAttribute("error", 3);
        }

        return new RedirectView("/slots");
    }
}