package com.api.parkingcontrol.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.parkingcontrol.models.ResidentModel;

public interface ResidentRepository extends JpaRepository<ResidentModel, UUID>{

    boolean existsByName(String name);
    
}
