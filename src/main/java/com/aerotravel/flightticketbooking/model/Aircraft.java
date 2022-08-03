package com.aerotravel.flightticketbooking.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft {
    @Id
    @GeneratedValue
    private long aircraftId;
    private String manufacturer;
    private String model;
    private Integer numberOfSeats;


    @OneToMany(mappedBy = "aircraft")
    private List<Flight> flights = new ArrayList<>();


    public Aircraft() {
    }

    public Aircraft( String manufacturer, String model, Integer numberOfSeats) {

        this.manufacturer = manufacturer;
        this.model = model;
        this.numberOfSeats = numberOfSeats;
    }

    public long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        String aircraftName = "";

        switch ((int) getAircraftId()) {
            case 1:
                aircraftName = "Aero-K " + "A320-214";
                break;
            case 2:
                aircraftName = "AirBusan " + "A320-232";
                break;
            case 3:
                aircraftName = "AirSeoul " + "A321-231";
                break;
            case 4:
                aircraftName = "Asiana " + "A321-251NX";
                break;
            case 5:
                aircraftName = "FlyGangwon " + "B737-800";
                break;
            case 6:
                aircraftName = "HiAir " + "ATR 72-500";
                break;
            case 7:
                aircraftName = "Jeju " + "B737-800";
                break;
            case 8:
                aircraftName = "JinAir " + "B777-200ER";
                break;
            case 9:
                aircraftName = "KoreanAir " + "B737-9B5ER";
                break;
            case 10:
                aircraftName = "Tway " + "A330-300";
                break;
        }
        return aircraftName;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }



    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
