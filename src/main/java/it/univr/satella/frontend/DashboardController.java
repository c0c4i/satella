package it.univr.satella.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.univr.satella.frontend.models.ChartData;
import it.univr.satella.sensors.Sample;
import it.univr.satella.sensors.SensorBundle;
import it.univr.satella.station.StationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class DashboardController {

    @Autowired
    private StationManager stationManager;

    @RequestMapping("/")
    public String index(Model model) {
        List<SensorBundle> sensors = stationManager.getSensors();
        List<ChartData> data = new ArrayList<>();
        sensors.forEach(sensorBundle -> {
            List<Sample> samples = stationManager.getChartOfSensorOfWeek(sensorBundle.getId());
            ChartData chart = new ChartData(sensorBundle, getXAxisFromSample(samples), getYAxisFromSample(samples) );
            data.add(chart);
        });

        model.addAttribute("data", data);
        return "index";
    }

    private String getListAsJson(Map<String, ChartData> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }

    public List<String> getXAxisFromSample(List<Sample> samples) {
        List<String> x = new ArrayList<>();
        for (Sample sample: samples) {
            x.add(sample.time.toString());
        }
        return x;
    }

    public List<Float> getYAxisFromSample(List<Sample> samples) {
        List<Float> y = new ArrayList<>();
        for (Sample sample: samples) {
            y.add(sample.measure);
        }
        return y;
    }
}