package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.repository.FlightRepository;
import com.aerotravel.flightticketbooking.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }
    @Override
    public Page<Flight> getAllFlightsPaged(Pageable pageable) {
        int page = (pageable.getPageNumber() ==0) ? 0:(pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page,20,Sort.by("departureAirport"));
        return flightRepository.findAll(pageable);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    @Override
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public void deleteFlightById(long flightId) {
        flightRepository.deleteById(flightId);
    }

    @Override
    public List<Flight> getAllFlightsByAirportAndDepartureTime(Airport depAirport, Airport destAirport, LocalDate depDate) {
        return flightRepository.findAllByDepartureAirportEqualsAndDestinationAirportEqualsAndDepartureDateEquals(depAirport, destAirport, depDate);
    }
    public List<Flight> getAllFlightsByAirportTime(Airport depAirport, LocalDate depDate){
        return flightRepository.findAllByDepartureAirportEqualsAndDepartureDateEquals(depAirport, depDate);
    }

/*    @Override
    public List<Flight> getAllFlightsByAirportAndDepartureTime2(Integer depAirport, Integer destAirport, String depDate) {
        return flightRepository.findAllFlightWithAnnotation2(depAirport,destAirport,depDate);
    }*/
}
