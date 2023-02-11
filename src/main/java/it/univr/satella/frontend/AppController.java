package it.univr.satella.frontend;

import it.univr.satella.station.StationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private StationManager stationManager;

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}