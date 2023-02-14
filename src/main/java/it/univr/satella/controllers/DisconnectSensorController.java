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
public class DisconnectSensorController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SlotService slotService;

    @RequestMapping("/slots/{id}/disconnect")
    public RedirectView connectSensor(@PathVariable("id") int id, RedirectAttributes attributes) {


        boolean result = slotService.detachSensorFromSlot(id);

        if(result) {
            attributes.addFlashAttribute("successType", 2);
            attributes.addFlashAttribute("success", "Sensore scollegato con successo!");
        } else {
            attributes.addFlashAttribute("errorType", 4);
            attributes.addFlashAttribute("error", "Impossibile scollegare il sensore!");
        }

        return new RedirectView("/slots");
    }
}