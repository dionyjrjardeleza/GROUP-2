package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.MTScheduleMeeting;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.MTScheduleMeetingRepository;
import com.oopclass.breadapp.services.IMTScheduleMeetingService;

@Service
public class MTScheduleMeetingService implements IMTScheduleMeetingService {

    @Autowired
    private MTScheduleMeetingRepository mtschedulemeetingRepository;

    @Override
    public MTScheduleMeeting save(MTScheduleMeeting entity) {
        return mtschedulemeetingRepository.save(entity);
    }

    @Override
    public MTScheduleMeeting update(MTScheduleMeeting entity) {
        return mtschedulemeetingRepository.save(entity);
    }

    @Override
    public void delete(MTScheduleMeeting entity) {
        mtschedulemeetingRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mtschedulemeetingRepository.deleteById(id);
    }

    @Override
    public MTScheduleMeeting find(Long id) {
        return mtschedulemeetingRepository.findById(id).orElse(null);
    }

    @Override
    public List<MTScheduleMeeting> findAll() {
        return mtschedulemeetingRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<MTScheduleMeeting> mtschedulemeetings) {
        mtschedulemeetingRepository.deleteInBatch(mtschedulemeetings);
    }

}
