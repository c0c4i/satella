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
import org.springframework.web.bind.annotation.RequestParam;
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
        return "sensors/new";
    }

    @RequestMapping("/sensors/new/send")
    public String create(
            @RequestParam(name="model-name", required=true) String modelName,
            @RequestParam(name="ampere-min", required=true) String ampereMin,
            @RequestParam(name="ampere-max", required=true) String ampereMax,
            @RequestParam(name="voltage-min", required=true) String voltageMin,
            @RequestParam(name="voltage-max", required=true) String voltageMax,
            RedirectAttributes attributes) {

            int error = -1;
            if(!validateStringWithoutSpaces(modelName)) error = 1;
            if(!validateFloat(ampereMin)) error = 2;
            if(!validateFloat(ampereMax)) error = 3;
            if(!validateFloat(voltageMin)) error = 4;
            if(!validateFloat(voltageMax)) error = 5;

            if(error != -1) {
                attributes.addFlashAttribute("error", error);
                attributes.addFlashAttribute("ampere-min", ampereMin);
                attributes.addFlashAttribute("ampere-max", ampereMax);
                attributes.addFlashAttribute("voltage-min", voltageMin);
                attributes.addFlashAttribute("voltage-max", voltageMax);
                return RedirectView("");
            }


            Sensor sensor = new Sensor(modelName, voltageMin, voltageMax, ampereMin, ampereMax);
            sensorService.addSensor(sensor);
            return "redirect:/sensors";
    }

    private static boolean validateFloat(String input) {
        try {
            float f = Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateStringWithoutSpaces(String input) {
        if (input == null) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                return false;
            }
        }
        return true;
    }

}