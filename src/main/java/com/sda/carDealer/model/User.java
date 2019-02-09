package com.sda.carDealer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String login;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @OneToOne
    @JoinColumn(name="operatorId")
    private Operator operator;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="UserRole", joinColumns = @JoinColumn(name="userId"))
    @Column(name = "roleName")
    private List<String> roles = new ArrayList<>();

}
