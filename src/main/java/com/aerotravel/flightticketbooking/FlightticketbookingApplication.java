package com.aerotravel.flightticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
public class FlightticketbookingApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(FlightticketbookingApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FlightticketbookingApplication.class);
    }

    public void addViewController(ViewControllerRegistry registry)
    {
        registry.addViewController("/static/highcharts").setViewName("static/highcharts");
    }
}
