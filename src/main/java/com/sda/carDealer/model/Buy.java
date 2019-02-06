package com.sda.carDealer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private Timestamp date;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="carId")
    private Car car;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "lastOwner")
    private Operator operator;
}
