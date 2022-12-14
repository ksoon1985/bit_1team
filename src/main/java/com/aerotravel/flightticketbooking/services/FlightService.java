package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface FlightService {
    public abstract Page<Flight> getAllFlightsPaged(Pageable pageNum);
    public abstract List<Flight> getAllFlights();
    public abstract Flight getFlightById(long flightId);
    public abstract Flight saveFlight(Flight flight);
    public abstract void deleteFlightById(long flightId);
    public abstract List<Flight> getAllFlightsByAirportAndDepartureTime(Airport depAirport, Airport destAirport, LocalDate depDate);
    public abstract List<Flight> getAllFlightsByAirportTime(Airport depAirport, LocalDate depDate);

    //public abstract List<Flight> getAllFlightsByAirportAndDepartureTime2(Integer depAirport, Integer destAirport, String depDate);

}
