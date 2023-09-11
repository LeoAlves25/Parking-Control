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

import com.api.parkingcontrol.DTOs.ApartmentDTO;
import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ApartmentService;
import com.api.parkingcontrol.services.ParkingSpotService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/apartment")
public class ApartmentController {
    
    final ApartmentService apartmentService;
    final ParkingSpotService parkingSpotService;

    public ApartmentController(ApartmentService apartmentService, ParkingSpotService parkingSpotService) {
        this.apartmentService = apartmentService;
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveApartment(@RequestBody @Valid ApartmentDTO apartmentDTO){

        if(apartmentService.existByApartmentNumberAndBlock(apartmentDTO.getApartmentNumber(), apartmentDTO.getBlock()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking spot number already exists");

        if(apartmentDTO.getParkingSpotModel() == null){
            ParkingSpotModel parkingSpotModel = new ParkingSpotModel();

            parkingSpotModel.setParkingSpotNumber(apartmentDTO.getApartmentNumber()+apartmentDTO.getBlock());

            parkingSpotService.save(parkingSpotModel);

            apartmentDTO.setParkingSpotModel(parkingSpotModel);
        }
        
        var apartmentModel = new ApartmentModel();
        BeanUtils.copyProperties(apartmentDTO, apartmentModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.save(apartmentModel));
    }

    @GetMapping
    public ResponseEntity<Page<ApartmentModel>> getAllApartments(@PageableDefault(page = 0, size = 10, sort="id") Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.findAll(pageable));
    }

    @GetMapping("/block/{block}")
    public ResponseEntity<Object> getAllApartmentsByBlock(@PathVariable(value = "block") String block, @PageableDefault(page = 0, size = 10, sort="id") Pageable pageable){

        Page<ApartmentModel> apartmentModels = apartmentService.findAllByBlock(block, pageable);

        if(apartmentModels.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartments not found");

        return ResponseEntity.status(HttpStatus.OK).body(apartmentModels);
    }

    @GetMapping("/apartment-number/{apartmentNumber}")
    public ResponseEntity<Object> getApartmentByApartmentNumber(@PathVariable(value = "apartmentNumber") String apartmentNumber){
        Optional<ApartmentModel> apartmentModel = apartmentService.findByApartmentNumber(apartmentNumber);

        if(!apartmentModel.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");

        return ResponseEntity.status(HttpStatus.OK).body(apartmentModel.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneApartment(@PathVariable(value = "id") UUID id){
        Optional<ApartmentModel> apartmentModel = apartmentService.findById(id);

        if(!apartmentModel.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");

        return ResponseEntity.status(HttpStatus.OK).body(apartmentModel.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApartment(@PathVariable(value="id") UUID id){
        Optional<ApartmentModel> apartmentModel = apartmentService.findById(id);

        if(!apartmentModel.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");

        apartmentService.delete(apartmentModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Apartment deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateApartment(@PathVariable(value="id") UUID id, @RequestBody @Valid ApartmentDTO apartmentDTO){
        Optional<ApartmentModel> apartmentModelOptional = apartmentService.findById(id);

        if(!apartmentModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");

        var apartmentModel = new ApartmentModel();

        BeanUtils.copyProperties(apartmentDTO, apartmentModel);
        apartmentModel.setId(apartmentModelOptional.get().getId());

        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.update(apartmentModel));
    }

}
