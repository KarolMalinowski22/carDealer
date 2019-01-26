package com.sda.carDealer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String vin;
    private String manufacturer;
    private String model;
    @Column(name="oc_insurance_number")
    private String ocInsuranceNumber;
    @Column(name="registration_number")
    private String registrationNumber;
    @Column(name="fuel_type")
    private String fuelType;
    private Long mileage;
    @Column(name="engine_type")
    private String engineType;
    private String power;
    private String transmission;
    @Column(name="test_drive_count")
    private Long testDriveCount;
    private String description;
    @Column(name="production_year")
    private String productionYear;
}
