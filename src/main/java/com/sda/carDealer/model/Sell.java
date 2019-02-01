package com.sda.carDealer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(5000)
    private BigDecimal price;
    private Timestamp date;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "carId")
    private Car car;
}
