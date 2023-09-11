package com.api.parkingcontrol.DTOs;

import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.CarModel;
import com.api.parkingcontrol.models.ParkingSpotModel;

import jakarta.validation.constraints.NotBlank;

public class ResidentDTO {
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    private CarModel car;
    
    private ApartmentModel apartment;

    private ParkingSpotModel parkingSpotModel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarPlate() {
        return car.getLicensePlateCar();
    }

    public String getApartmentNumber(){
        return apartment.getApartmentNumber();
    }

    public String getBlock(){
        return apartment.getBlock();
    }

    public String getParkingSpot(){
        return parkingSpotModel.getParkingSpotNumber();
    }
}
