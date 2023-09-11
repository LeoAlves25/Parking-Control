package com.api.parkingcontrol.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.parkingcontrol.models.CarModel;

public interface CarRepository extends JpaRepository<CarModel, UUID>{

    boolean existsByLicensePlateCar(String licensePlateCar);

    Optional<CarModel> findByLicensePlateCar(String licensePlate);

    Optional<CarModel> findByBrandCar(String brandCar);
    
}
