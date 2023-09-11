package com.api.parkingcontrol.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.parkingcontrol.models.ApartmentModel;

public interface ApartmentRepository extends JpaRepository<ApartmentModel, UUID>{

    Page<ApartmentModel> findAllByBlock(String block, Pageable pageable);

	boolean existsByNumberAndBlock(String apartmentNumber, String block);

    Optional<ApartmentModel> findByNumber(String apartmentNumber);
    
}
