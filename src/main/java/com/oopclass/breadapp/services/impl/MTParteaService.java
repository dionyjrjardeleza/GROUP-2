package com.oopclass.breadapp.services.impl;

import com.oopclass.breadapp.models.MTPartea;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oopclass.breadapp.repository.MTParteaRepository;
import com.oopclass.breadapp.services.IMTParteaService;


@Service
public class MTParteaService implements IMTParteaService {

    @Autowired
    private MTParteaRepository mtparteaRepository;

    @Override
    public MTPartea save(MTPartea entity) {
        return mtparteaRepository.save(entity);
    }

    @Override
    public MTPartea update(MTPartea entity) {
        return mtparteaRepository.save(entity);
    }

    @Override
    public void delete(MTPartea entity) {
        mtparteaRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        mtparteaRepository.deleteById(id);
    }

    @Override
    public MTPartea find(Long id) {
        return mtparteaRepository.findById(id).orElse(null);
    }

    @Override
    public List<MTPartea> findAll() {
        return mtparteaRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<MTPartea> mtparteas) {
        mtparteaRepository.deleteInBatch(mtparteas);
    }

}
