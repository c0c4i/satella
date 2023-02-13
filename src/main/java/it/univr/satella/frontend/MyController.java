package it.univr.satella.frontend;

import it.univr.satella.frontend.models.MyData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class MyController {
    @GetMapping("/show-chart")
    public String showChart(Model model) {
        List<MyData> data = getData();
        model.addAttribute("data", data);
        return "show-chart";
    }

    private List<MyData> getData() {
        List<MyData> data = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String label = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            int value = random.nextInt(100);
            data.add(new MyData(label, value));
        }

        return data;
    }
}