package com.api.parkingcontrol.DTOs;

import com.api.parkingcontrol.models.ResidentModel;

import jakarta.validation.constraints.NotBlank;

public class ParkingSpotDTO {

    @NotBlank
    private String parkingSpotNumber;

    private ResidentModel residentModel;

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public ResidentModel getResidentModel() {
        return residentModel;
    }

    public void setResidentModel(ResidentModel residentModel) {
        this.residentModel = residentModel;
    }
    
}
