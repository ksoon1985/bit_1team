package com.aerotravel.flightticketbooking.services;


import com.aerotravel.flightticketbooking.model.*;

import java.time.LocalDate;
import java.util.List;

public interface VerifyPassengerService {
    public abstract VerifyPassenger saveVerifyPassenger(VerifyPassenger verifyPassenger);
    public abstract List<VerifyPassenger> getAllVerifyPassenger(User user);

    public abstract void deleteVerifyPassengerById(Long verifypassengerId);

}
