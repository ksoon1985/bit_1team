package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface PassengerService {
    public abstract Page<Passenger> getAllPassengersPaged(int pageNum);
    public abstract List<Passenger> getAllPassengers();
    public abstract Passenger getPassengerById(Long passengerId);
    public abstract Passenger savePassenger(Passenger passenger);
    public abstract void deletePassengerById(Long passengerId);
    public abstract HashMap<String, Integer> getDestinationAirportNameAndCount(Passenger passenger);
}
