package com.api.parkingcontrol.DTOs;

import com.api.parkingcontrol.models.ResidentModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CarDTO {
    
    @NotBlank
    @Size(min = 7, max = 7, message = "License plate must have 7 characters") 
    private String licensePlateCar;

    @NotBlank   
    private String brandCar;

    @NotBlank   
    private String modelCar;

    @NotBlank   
    private String colorCar;

    private ResidentModel residentModel;

    public String getLicensePlateCar() {
        return licensePlateCar;
    }

    public void setLicensePlateCar(String licensePlateCar) {
        this.licensePlateCar = licensePlateCar;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public ResidentModel getResidentModel() {
        return residentModel;
    }

    public void setResidentModel(ResidentModel residentModel) {
        this.residentModel = residentModel;
    }
}
