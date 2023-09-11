package com.api.parkingcontrol.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.api.parkingcontrol.DTOs.ResidentDTO;
import com.api.parkingcontrol.models.ResidentModel;
import com.api.parkingcontrol.services.ApartmentService;
import com.api.parkingcontrol.services.CarService;
import com.api.parkingcontrol.services.ResidentService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/resident")
public class ResidentController {

    final ResidentService residentService;
    final CarService carService;
    final ApartmentService apartmentService;

    public ResidentController(ResidentService residentService, CarService carService, ApartmentService apartmentService) {
        this.residentService = residentService;
        this.carService = carService;
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<Object> saveResident(@RequestBody @Valid ResidentDTO residentDTO) {

        if (residentService.existByName(residentDTO.getName().toUpperCase()))
            return ResponseEntity.badRequest().body("Resident already exists");

        if (carService.existsByLicensePlateCar(residentDTO.getCarPlate().toUpperCase()))
            return ResponseEntity.badRequest().body("License plate already exists");

        if (apartmentService.existByApartmentNumberAndBlock(residentDTO.getApartmentNumber(),
                residentDTO.getBlock().toUpperCase()))
            return ResponseEntity.badRequest().body("Apartment already occupied");

        var residentModel = new ResidentModel();
        BeanUtils.copyProperties(residentDTO, residentModel);
        residentModel.setName(residentModel.getName().toUpperCase());

        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.save(residentModel));
    }

    @GetMapping
    public ResponseEntity<Page<ResidentModel>> getAllResidents(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(residentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneResident(@PathVariable(value = "id") UUID id) {

        Optional<ResidentModel> residentOptional = residentService.findById(id);

        if (residentOptional.isEmpty())
            return ResponseEntity.badRequest().body("Resident not found");

        return ResponseEntity.status(HttpStatus.OK).body(residentOptional);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteResident(@PathVariable(value = "id") UUID id) {

        Optional<ResidentModel> residentOptional = residentService.findById(id);

        if (residentOptional.isEmpty())
            return ResponseEntity.badRequest().body("Resident not found");

        residentService.delete(residentOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Resident deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateResident(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ResidentDTO residentDTO) {

        Optional<ResidentModel> residentOptional = residentService.findById(id);

        if (residentOptional.isEmpty())
            return ResponseEntity.badRequest().body("Resident not found");

        if (residentService.existByName(residentDTO.getName().toUpperCase()))
            return ResponseEntity.badRequest().body("Resident already exists");

        if (carService.existsByLicensePlateCar(residentDTO.getCarPlate().toUpperCase()) && !residentOptional.get().getLicensePlateOfCar().equals(residentDTO.getCarPlate().toUpperCase()))
            return ResponseEntity.badRequest().body("License plate already exists");

        if (apartmentService.existByApartmentNumberAndBlock(residentDTO.getApartmentNumber(),
                residentDTO.getBlock().toUpperCase()) && residentOptional.get().hasApartment(residentDTO.getApartmentNumber(), residentDTO.getBlock().toUpperCase()))
            return ResponseEntity.badRequest().body("Apartment already occupied");

        var residentModel = residentOptional.get();
        BeanUtils.copyProperties(residentDTO, residentModel);
        residentModel.setName(residentModel.getName().toUpperCase());

        return ResponseEntity.status(HttpStatus.OK).body(residentService.save(residentModel));
    }

}
