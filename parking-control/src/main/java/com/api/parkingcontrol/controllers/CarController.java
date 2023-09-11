package com.api.parkingcontrol.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.DTOs.CarDTO;
import com.api.parkingcontrol.models.CarModel;
import com.api.parkingcontrol.services.CarService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/car")
public class CarController {
    
    final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCar(@RequestBody @Valid CarDTO carDTO){
        
        if(carService.existsByLicensePlateCar(carDTO.getLicensePlateCar().toUpperCase()))
            return ResponseEntity.badRequest().body("License plate already exists");

        var carModel = new CarModel();
        BeanUtils.copyProperties(carDTO, carModel);
        carModel.setLicensePlateCar(carModel.getLicensePlateCar().toUpperCase());
        carModel.setBrandCar(carModel.getBrandCar().toLowerCase());

        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(carModel));
    }

    @GetMapping
    public ResponseEntity<Page<CarModel>> getAllCars(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){

        Page<CarModel> carModelPage = carService.findAll(pageable);

        for (CarModel carModel : carModelPage) {
            carModel.setBrandCar(carModel.getBrandCar().substring(0, 1).toUpperCase().concat(carModel.getBrandCar().substring(1)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(carModelPage);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Object> getOneCar(@PathVariable(value = "id") UUID id){
        Optional<CarModel> carModelOptional = carService.findById(id);

        if(!carModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");

        carModelOptional.get().setBrandCar(carModelOptional.get().getBrandCar().substring(0, 1).toUpperCase().concat(carModelOptional.get().getBrandCar().substring(1)));

        return ResponseEntity.status(HttpStatus.OK).body(carModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable(value = "id") UUID id){
        Optional<CarModel> carModelOptional = carService.findById(id);

        if(!carModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");

        carService.delete(carModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Car deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable(value = "id") UUID id, @RequestBody @Valid CarDTO carDTO){
        Optional<CarModel> carModelOptional = carService.findById(id);

        if(!carModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");

        if(carService.existsByLicensePlateCar(carDTO.getLicensePlateCar()) && !carModelOptional.get().getLicensePlateCar().equals(carDTO.getLicensePlateCar()))
            return ResponseEntity.badRequest().body("License plate already exists");

        var carModel = carModelOptional.get();
        BeanUtils.copyProperties(carDTO, carModel);
        carModel.setLicensePlateCar(carModel.getLicensePlateCar().toUpperCase());
        carModel.setBrandCar(carModel.getBrandCar().toLowerCase());

        return ResponseEntity.status(HttpStatus.OK).body(carService.update(carModel));
    }


    @GetMapping("/licensePlate/{licensePlate}")
    public ResponseEntity<Object> getCarByLicensePlate(@PathVariable(value = "licensePlate") String licensePlate){

        licensePlate = licensePlate.toUpperCase();

        Optional<CarModel> carModelOptional = carService.findByLicensePlateCar(licensePlate);

        if(!carModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");

        

        return ResponseEntity.status(HttpStatus.OK).body(carModelOptional.get());
    }

    @GetMapping("/brandCar/{brandCar}")
    public ResponseEntity<Object> getCarByBrandCar(@PathVariable(value = "brandCar") String brandCar){

        brandCar = brandCar.toLowerCase();

        Optional<CarModel> carModelOptional = carService.findByBrandCar(brandCar);

        if(!carModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");

        return ResponseEntity.status(HttpStatus.OK).body(carModelOptional.get());
    }

}
