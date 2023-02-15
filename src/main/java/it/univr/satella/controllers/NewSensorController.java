package it.univr.satella.controllers;

import it.univr.satella.model.Sensor;
import it.univr.satella.model.Slot;
import it.univr.satella.service.SensorService;
import it.univr.satella.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class NewSensorController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SlotService slotService;

    @RequestMapping("/sensors/new")
    public String newSensor(Model model) {
        Sensor sensor = (Sensor) model.getAttribute("sensor");
        if(sensor != null)
            model.addAttribute("form", sensor);
        else
            model.addAttribute("form", new Sensor());
        return "sensors/new";
    }

    @PostMapping("/sensors/new/send")
    public RedirectView create(
            @ModelAttribute("form") Sensor sensor,
            RedirectAttributes attributes, Model model) {

            int validate = sensor.isValid();
            if(validate != -1) {
                attributes.addFlashAttribute("errorType", validate);
                attributes.addFlashAttribute("error", Sensor.getInvalidMessage(validate));
                attributes.addFlashAttribute("sensor", sensor);
                return new RedirectView("/sensors/new");
            }

            sensorService.addSensor(sensor);
            attributes.addFlashAttribute("successType", 1);
            attributes.addFlashAttribute("success", "Sensore creato con successo!");
            return new RedirectView("/sensors");
    }
}