package com.api.parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.models.ResidentModel;
import com.api.parkingcontrol.repositories.ResidentRepository;

import jakarta.transaction.Transactional;

@Service
public class ResidentService {

    final ResidentRepository residentRepository;
    
    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Transactional
    public ResidentModel save(ResidentModel residentModel) {
        return residentRepository.save(residentModel);
    }

    public Page<ResidentModel> findAll(Pageable pageable) {
        return residentRepository.findAll(pageable);
    }

    public Optional<ResidentModel> findById(UUID id) {
        return residentRepository.findById(id);
    }

    @Transactional
    public void delete(ResidentModel residentModel) {
        residentRepository.delete(residentModel);
    }

    public Object update(ResidentModel residentModel) {
        return residentRepository.save(residentModel);
    }

    public boolean existByName(String name) {
        return residentRepository.existsByName(name);
    }

}
