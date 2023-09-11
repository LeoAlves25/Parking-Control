package com.api.parkingcontrol.models;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_RESIDENT")
public class ResidentModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 70)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = true)
    private CarModel car;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id", nullable = true)
    private ApartmentModel apartment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public ApartmentModel getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentModel apartment) {
        this.apartment = apartment;
    }

    public Object getLicensePlateOfCar() {
        return car.getLicensePlateCar();
    }

    public boolean hasApartment(String apartmentNumber, String block){
        return apartment.getApartmentNumber().equals(apartmentNumber) && apartment.getBlock().equals(block);
    }
}
