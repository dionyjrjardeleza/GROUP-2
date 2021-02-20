package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.MTReservation;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.MTReservationRepository;
import com.oopclass.breadapp.services.IMTReservationService;



/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Service
public class MTReservationService implements IMTReservationService {

    @Autowired
    private MTReservationRepository mtreservationRepository;

    @Override
    public MTReservation save(MTReservation entity) {
        return mtreservationRepository.save(entity);
    }

    @Override
    public MTReservation update(MTReservation entity) {
        return mtreservationRepository.save(entity);
    }

    @Override
    public void delete(MTReservation entity) {
        mtreservationRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mtreservationRepository.deleteById(id);
    }

    @Override
    public MTReservation find(Long id) {
        return mtreservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<MTReservation> findAll() {
        return mtreservationRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<MTReservation> mtreservations) {
        mtreservationRepository.deleteInBatch(mtreservations);
    }

}
