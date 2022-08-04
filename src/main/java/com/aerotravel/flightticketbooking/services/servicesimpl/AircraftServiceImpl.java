package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.repository.AircraftRepository;
import com.aerotravel.flightticketbooking.services.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {
    @Autowired
    private AircraftRepository aircraftRepository;

//    @Autowired
//    public AircraftServiceImpl(AircraftRepository aircraftRepository){
//        this.aircraftRepository = aircraftRepository;
//    }

    @Override
    public Page<Aircraft> getAllAircraftsPaged(Pageable pageable) {
        int page = (pageable.getPageNumber() ==0) ? 0:(pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page,20,Sort.by("model"));
        return aircraftRepository.findAll(pageable);
    }

    @Override
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    @Override
    public Aircraft getAircraftById(Long aircraftId) {
        return aircraftRepository.findById(aircraftId).orElse(null);
    }

    @Override
    public Aircraft saveAircraft(Aircraft aircraft) {
        if(aircraft==null) throw new IllegalArgumentException();
        return aircraftRepository.save(aircraft);
    }

    @Override
    public void deleteAircraftById(Long aircraftId) {
        aircraftRepository.deleteById(aircraftId);
    }
}
