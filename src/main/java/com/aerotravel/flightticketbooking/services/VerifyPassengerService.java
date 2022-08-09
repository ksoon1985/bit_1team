package com.aerotravel.flightticketbooking.services;


import com.aerotravel.flightticketbooking.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface VerifyPassengerService {
    public abstract VerifyPassenger saveVerifyPassenger(VerifyPassenger verifyPassenger);
    public abstract Page<VerifyPassenger> getAllVerifyPassenger(User user, Pageable pageable);

    public abstract Page<VerifyPassenger> getAllVerifyPassenger(Pageable pageable);

    public abstract void deleteVerifyPassengerById(Long verifypassengerId);

}
