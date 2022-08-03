package com.aerotravel.flightticketbooking.repository;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByDepartureAirportEqualsAndDestinationAirportEqualsAndDepartureDateEquals(Airport depAirport, Airport destAirport, LocalDate depDate);
    @Query(value = "SELECT destination_airport_airport_id,count(*) FROM flight Group by destination_airport_airport_id", nativeQuery = true)
    HashMap<String,Long> findByDestinationAirportEqualsAndDepartureDateEquals(Airport depAirport, LocalDate deptTime);
}
