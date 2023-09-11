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
@Table(name = "TB_APARTMENT")
public class ApartmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 30)
    private String number;

    @Column(nullable = false, length = 30)
    private String block;

    @OneToOne(mappedBy = "apartment")
    private ResidentModel resident;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkingSpot_id", referencedColumnName = "id", nullable = true)
    private ParkingSpotModel parkingSpot;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
}
