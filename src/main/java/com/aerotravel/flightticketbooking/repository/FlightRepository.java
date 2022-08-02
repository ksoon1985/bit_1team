package com.aerotravel.flightticketbooking.repository;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByDepartureAirportEqualsAndDestinationAirportEqualsAndDepartureDateEquals(Airport depAirport, Airport destAirport, LocalDate depDate);

    @Query(value = "select * from Flight where departure_airport_airport_id = ?1 and destination_airport_airport_id = ?2 and departure_Date = ?3" , nativeQuery = true)
    List<Flight> findAllFlightWithAnnotation2(Integer departureAirport, Integer destinationAirport , String departureTime);

}
