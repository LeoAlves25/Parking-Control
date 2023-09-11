package com.api.parkingcontrol.DTOs;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.models.ResidentModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ApartmentDTO {
    
    @NotBlank
    @Size(min = 3, max = 4, message = "Apartment number must be between 3 and 4 characters")
    private String number;

    @NotBlank
    @Size(min = 1, max = 1, message = "Block must be 1 character")
    private String block;

    private ResidentModel residentModel;

    private ParkingSpotModel parkingSpotModel;

    public String getApartmentNumber() {
        return number;
    }

    public void setApartmentNumber(String number) {
        this.number = number;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public ResidentModel getResidentModel() {
        return residentModel;
    }

    public void setResidentModel(ResidentModel residentModel) {
        this.residentModel = residentModel;
    }

    public ParkingSpotModel getParkingSpotModel() {
        return parkingSpotModel;
    }

    public void setParkingSpotModel(ParkingSpotModel parkingSpotModel) {
        this.parkingSpotModel = parkingSpotModel;
    }
}
