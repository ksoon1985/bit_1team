package com.aerotravel.flightticketbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class GraphController {
    @GetMapping("/barChart")
    public String barChart(Model model)
    {




        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        data.put("Ashish", 30);
        data.put("Ankit", 50);
        data.put("Gurpreet", 70);
        data.put("Mohit", 90);
        data.put("Manish", 25);
        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());
        return "highcharts/barChart";

    }

    @GetMapping("/pieChart")
    public String pieChart(Model model) {
        model.addAttribute("pass", 90);
        model.addAttribute("fail", 10);
        return "highcharts/pieChart";

    }
}
