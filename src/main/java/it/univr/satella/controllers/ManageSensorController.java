package it.univr.satella.controllers;

import it.univr.satella.model.Sensor;
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

@Controller
public class ManageSensorController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SlotService slotService;

    @RequestMapping("/sensors/{id}")
    public String sensorDetails(@PathVariable("id") String modelName, Model model, RedirectAttributes attributes) {
        Sensor sensor = sensorService.findSensorByModelName(modelName);
        if(sensor == null) {
            return "sensors/_id/not-found";
        }
        model.addAttribute("sensor", sensor);
        return "sensors/_id/index";
    }

    @RequestMapping("/sensors/{id}/update")
    public RedirectView create(
            @RequestParam(name="model-name", required=false) String modelName,
            @RequestParam(name="ampere-min", required=false) String ampereMin,
            @RequestParam(name="ampere-max", required=false) String ampereMax,
            @RequestParam(name="voltage-min", required=false) String voltageMin,
            @RequestParam(name="voltage-max", required=false) String voltageMax,
            RedirectAttributes attributes) {

            int error = -1;
            if(!validateStringWithoutSpaces(modelName)) error = 1;
            else if(!validateFloat(ampereMin)) error = 2;
            else if(!validateFloat(ampereMax)) error = 3;
            else if(!validateFloat(voltageMin)) error = 4;
            else if(!validateFloat(voltageMax)) error = 5;
            else if(Float.parseFloat(ampereMin) >= Float.parseFloat(ampereMax)) error = 6;
            else if(Float.parseFloat(voltageMin) >= Float.parseFloat(voltageMax)) error = 7;

            if(error != -1) {
                attributes.addFlashAttribute("errorType", error);
                attributes.addFlashAttribute("error", getError(error));
                attributes.addFlashAttribute("modelName", modelName);
                attributes.addFlashAttribute("ampereMin", ampereMin);
                attributes.addFlashAttribute("ampereMax", ampereMax);
                attributes.addFlashAttribute("voltageMin", voltageMin);
                attributes.addFlashAttribute("voltageMax", voltageMax);
                return new RedirectView("/sensors/" + modelName);
            }

            Sensor sensor = new Sensor(modelName, Float.parseFloat(voltageMin), Float.parseFloat(voltageMax), Float.parseFloat(ampereMin), Float.parseFloat(ampereMax));
            sensorService.addSensor(sensor);
            attributes.addFlashAttribute("successType", 1);
            attributes.addFlashAttribute("success", "Sensore aggiornato con successo!");
            return new RedirectView("/sensors");
    }

    private static String getError(int error) {
        switch (error) {
            case 1:
                return "Il modello non può contenere spazi vuoti";
            case 2:
                return "Il campo \"Ampere minimo\" deve essere un numero";
            case 3:
                return "Il campo \"Ampere massimo\" deve essere un numero";
            case 4:
                return "Il \"Voltaggio minimo\" deve essere un numero";
            case 5:
                return "Il \"Voltaggio minimo\" deve essere un numero";
            case 6:
                return "Il campo \"Ampere minimo\" deve essere inferiore al campo \"Ampere massimo\" ";
            case 7:
                return "Il \"Voltaggio minimo\" deve essere inferiore al \"Voltaggio massimo\" ";

        }
        return null;
    }

    private static boolean validateFloat(String input) {
        if(input == null) return false;
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