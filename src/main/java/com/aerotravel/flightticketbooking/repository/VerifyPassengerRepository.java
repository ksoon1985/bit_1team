package com.aerotravel.flightticketbooking.repository;


import com.aerotravel.flightticketbooking.model.User;
import com.aerotravel.flightticketbooking.model.VerifyPassenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface VerifyPassengerRepository extends JpaRepository<VerifyPassenger, Long>  {
    Page<VerifyPassenger> findAllByUser(User user,Pageable pageable);

}
