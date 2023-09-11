package com.api.parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.repositories.ApartmentRepository;

import jakarta.transaction.Transactional;

@Service
public class ApartmentService {
    
    final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Transactional
    public ApartmentModel save(ApartmentModel apartmentModel) {
        return apartmentRepository.save(apartmentModel);
    }

    public Page<ApartmentModel> findAll(Pageable pageable) {
        return apartmentRepository.findAll(pageable);
    }

    public Optional<ApartmentModel> findById(UUID id) {
        return apartmentRepository.findById(id);
    }

    @Transactional
    public void delete(ApartmentModel apartmentModel) {
        apartmentRepository.delete(apartmentModel);
    }

    public Object update(ApartmentModel apartmentModel) {
        return apartmentRepository.save(apartmentModel);
    }

    public boolean existByApartmentNumberAndBlock(String apartmentNumber, String block) {
        return apartmentRepository.existsByNumberAndBlock(apartmentNumber, block);
    }

    public Page<ApartmentModel> findAllByBlock(String block, Pageable pageable) {

        return apartmentRepository.findAllByBlock(block, pageable);
    }

    public Optional<ApartmentModel> findByApartmentNumber(String apartmentNumber) {
        return apartmentRepository.findByNumber(apartmentNumber);
    }

}
