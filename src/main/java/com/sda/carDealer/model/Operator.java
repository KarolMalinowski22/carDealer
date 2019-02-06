package com.sda.carDealer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "operators")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "id_card_number")
    private String idCardNumber;
    @ManyToMany(mappedBy = "operators")
    private List<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(firstName, operator.firstName) &&
                Objects.equals(lastName, operator.lastName) &&
                Objects.equals(idCardNumber, operator.idCardNumber);
    }

}

