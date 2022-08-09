package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.User;
import com.aerotravel.flightticketbooking.model.VerifyPassenger;
import com.aerotravel.flightticketbooking.repository.PassengerRepository;
import com.aerotravel.flightticketbooking.repository.VerifyPassengerRepository;
import com.aerotravel.flightticketbooking.services.VerifyPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VerifyPassengerServicelmpl implements VerifyPassengerService {
    private VerifyPassengerRepository verifyPassengerRepository;
    @Autowired
    public VerifyPassengerServicelmpl(VerifyPassengerRepository verifyPassengerRepository){
        this.verifyPassengerRepository = verifyPassengerRepository;
    }
    @Override
    public VerifyPassenger saveVerifyPassenger(VerifyPassenger verifyPassenger) {
        return verifyPassengerRepository.save(verifyPassenger);
    }
    public Page<VerifyPassenger> getAllVerifyPassenger(User user,Pageable pageable){
        int page = (pageable.getPageNumber() ==0) ? 0:(pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page,20, Sort.by("id"));
        return verifyPassengerRepository.findAllByUser(user,pageable);
    }
    public Page<VerifyPassenger> getAllVerifyPassenger(Pageable pageable) {
        int page = (pageable.getPageNumber() ==0) ? 0:(pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page,20, Sort.by("id"));
        return verifyPassengerRepository.findAll(pageable);
    }

    public void deleteVerifyPassengerById(Long verifypassengerId) {
        verifyPassengerRepository.deleteById(verifypassengerId);
    }

}
