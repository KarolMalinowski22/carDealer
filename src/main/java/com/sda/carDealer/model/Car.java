package com.sda.carDealer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity(name = "Cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 10, max = 20)
    private String vin;
    @NotNull
    private String manufacturer;
    @NotNull
    private String model;
    @NotNull
    @Column(name = "oc_insurance_number")
    private String ocInsuranceNumber;
    @NotNull
    @Column(name = "registration_number")
    private String registrationNumber;
    @NotNull
    @Column(name = "fuel_type")
    private String fuelType;
    @NotNull
    private Long mileage;
    @NotNull
    @Column(name = "engine_type")
    @NotNull
    private String engineType;
    @NotNull
    private String power;
    @NotNull
    private String transmission;
    @Column(name = "test_drive_count")
    private Long testDriveCount;
    @NotNull
    private String description;
    @NotNull
    @Column(name = "production_year")
    private String productionYear;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="ownership",
    joinColumns = @JoinColumn(name = "carId"),
    inverseJoinColumns = @JoinColumn(name = "customerId"))
    private List<Customer> customers;
}
