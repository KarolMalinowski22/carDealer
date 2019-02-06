package com.sda.carDealer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(5000)
    private BigDecimal price;
    private Timestamp date;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "nextOwner")
    private Operator operator;
}
