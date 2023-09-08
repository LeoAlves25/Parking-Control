package com.api.parkingcontrol.DTOs;

import com.api.parkingcontrol.models.ResidentModel;

import jakarta.validation.constraints.NotBlank;

public class ApartmentDTO {
    
    @NotBlank
    private String apartmentNumber;

    @NotBlank
    private String block;

    private ResidentModel residentModel;

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
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
}
