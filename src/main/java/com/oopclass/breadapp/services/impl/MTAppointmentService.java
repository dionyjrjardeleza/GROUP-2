package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.MTAppointment;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.MTAppointmentRepository;
import com.oopclass.breadapp.services.IMTAppointmentService;



/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Service
public class MTAppointmentService implements IMTAppointmentService {

    @Autowired
    private MTAppointmentRepository mtappointmentRepository;

    @Override
    public MTAppointment save(MTAppointment entity) {
        return mtappointmentRepository.save(entity);
    }

    @Override
    public MTAppointment update(MTAppointment entity) {
        return mtappointmentRepository.save(entity);
    }

    @Override
    public void delete(MTAppointment entity) {
        mtappointmentRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mtappointmentRepository.deleteById(id);
    }

    @Override
    public MTAppointment find(Long id) {
        return mtappointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<MTAppointment> findAll() {
        return mtappointmentRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<MTAppointment> mtappointments) {
        mtappointmentRepository.deleteInBatch(mtappointments);
    }

}
