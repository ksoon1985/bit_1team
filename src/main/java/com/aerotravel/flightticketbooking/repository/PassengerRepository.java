package com.aerotravel.flightticketbooking.repository;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query(value = "select airport_name,count(*) from passenger join fetch flight on passenger.flight_flight_id=flight.flight_id join fetch airport on flight.destination_airport_airport_id=airport.airport_id group by destination_airport_airport_id", nativeQuery = true)
    HashMap<String,Integer> FindDestinationAirportCountByFlightId(Passenger flightid);
}
