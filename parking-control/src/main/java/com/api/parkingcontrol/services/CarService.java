package com.api.parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.models.CarModel;
import com.api.parkingcontrol.repositories.CarRepository;

import jakarta.transaction.Transactional;

@Service
public class CarService {

    final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public CarModel save(CarModel carModel) {
        return carRepository.save(carModel);
    }

    public Page<CarModel> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Optional<CarModel> findById(UUID id) {
        return carRepository.findById(id);
    }

    @Transactional
    public void delete(CarModel carModel){
        carRepository.delete(carModel);
    }

    public Object update(CarModel carModel) {
        return carRepository.save(carModel);
    }
}
