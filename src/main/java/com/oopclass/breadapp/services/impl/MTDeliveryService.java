package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.MTDelivery;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.MTDeliveryRepository;
import com.oopclass.breadapp.services.IMTDeliveryService;

@Service
public class MTDeliveryService implements IMTDeliveryService {

    @Autowired
    private MTDeliveryRepository mtdeliveryRepository;

    @Override
    public MTDelivery save(MTDelivery entity) {
        return mtdeliveryRepository.save(entity);
    }

    @Override
    public MTDelivery update(MTDelivery entity) {
        return mtdeliveryRepository.save(entity);
    }

    @Override
    public void delete(MTDelivery entity) {
        mtdeliveryRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mtdeliveryRepository.deleteById(id);
    }

    @Override
    public MTDelivery find(Long id) {
        return mtdeliveryRepository.findById(id).orElse(null);
    }

    @Override
    public List<MTDelivery> findAll() {
        return mtdeliveryRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<MTDelivery> mtdeliverys) {
        mtdeliveryRepository.deleteInBatch(mtdeliverys);
    }

}
