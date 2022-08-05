package com.aerotravel.flightticketbooking.repository;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByDepartureAirportEqualsAndDestinationAirportEqualsAndDepartureDateEquals(Airport depAirport, Airport destAirport, LocalDate depDate);

    List<Flight> findAllByDepartureAirportEqualsAndDepartureDateEquals(Airport depAirport, LocalDate deptTime);

    @Query(value = "select aircraft_id, model, flight_charge, ranking " +
            "from " +
            "(select  b.aircraft_id, b.model , round(avg(flight_charge)) as flight_charge, rank() over(order by round(avg(flight_charge)) asc) as ranking " +
            "from flight as a " +
            "inner join aircraft as b " +
            "on a.aircraft_aircraft_id = b.aircraft_id " +
            "group by b.aircraft_id)as t " +
            "where ranking <= 5 ",nativeQuery = true)
    List<Object[]> getHighChartData1();
    @Query(value = "SELECT destination_airport_airport_id,count(*) FROM flight Group by destination_airport_airport_id", nativeQuery = true)
    HashMap<String,Long> findByDestinationAirportEqualsAndDepartureDateEquals(Airport depAirport, LocalDate deptTime);

}
