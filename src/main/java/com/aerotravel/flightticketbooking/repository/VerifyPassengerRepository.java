package com.aerotravel.flightticketbooking.repository;


import com.aerotravel.flightticketbooking.model.User;
import com.aerotravel.flightticketbooking.model.VerifyPassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface VerifyPassengerRepository extends JpaRepository<VerifyPassenger, Long>  {
    List<VerifyPassenger> findAllByUser(User user);
}
