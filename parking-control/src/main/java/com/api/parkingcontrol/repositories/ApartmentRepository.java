package com.api.parkingcontrol.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.parkingcontrol.models.ApartmentModel;

public interface ApartmentRepository extends JpaRepository<ApartmentModel, UUID>{
    
}
