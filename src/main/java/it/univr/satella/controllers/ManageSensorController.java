package it.univr.satella.controllers;

import it.univr.satella.model.Sensor;
import it.univr.satella.model.SlotCapabilities;
import it.univr.satella.service.SensorService;
import it.univr.satella.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ManageSensorController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SlotService slotService;

    @RequestMapping("/sensors/{id}")
    public String manageSensor(@PathVariable("id") String modelName, Model model) {
        Sensor sensor = (Sensor) model.getAttribute("sensor");
        if(sensor != null)
            model.addAttribute("form", sensor);
        else {
            sensor = sensorService.findSensorByModelName(modelName);
            if(sensor == null) {
                return "sensors/_id/not-found";
            }
            model.addAttribute("form", sensor);
        }
        return "sensors/_id/index";
    }

    @PostMapping("/sensors/{id}/update")
    public RedirectView manage(
        @ModelAttribute("form") Sensor sensor,
            RedirectAttributes attributes) {

            int validate = sensor.isValid();
            if(validate != -1) {
                attributes.addFlashAttribute("errorType", validate);
                attributes.addFlashAttribute("error", Sensor.getInvalidMessage(validate));
                attributes.addFlashAttribute("sensor", sensor);
                return new RedirectView("/sensors/" + sensor.getModelName());
            }

            SlotCapabilities slotCapabilities = slotService.getSlotCapabilitiesFromSensor(sensor);
            if(slotCapabilities != null) {
                int compatible = sensor.isCompatible(slotCapabilities);
                if (compatible != -1) {
                    attributes.addFlashAttribute("errorType", compatible);
                    attributes.addFlashAttribute("error", Sensor.getIncompatibleMessage(compatible, slotCapabilities));
                    attributes.addFlashAttribute("sensor", sensor);
                    return new RedirectView("/sensors/" + sensor.getModelName());
                }
            }

            sensorService.addSensor(sensor);
            attributes.addFlashAttribute("successType", 1);
            attributes.addFlashAttribute("success", "Sensore aggiornato con successo!");
            return new RedirectView("/sensors");
    }

    @GetMapping("/sensors/{id}/delete")
    public RedirectView manage(@PathVariable("id") String modelName, RedirectAttributes attributes) {
        Sensor sensor = sensorService.findSensorByModelName(modelName);
        if(sensor == null) {
            attributes.addFlashAttribute("errorType", 1);
            attributes.addFlashAttribute("error", "Il sensore selezionato non esiste!");
            return new RedirectView("/sensors");
        }

        slotService.removeSensorFromSlots(sensor);
        sensorService.deleteSensor(sensor);
        attributes.addFlashAttribute("successType", 2);
        attributes.addFlashAttribute("success", "Sensore eliminato con successo!");
        return new RedirectView("/sensors");
    }
}