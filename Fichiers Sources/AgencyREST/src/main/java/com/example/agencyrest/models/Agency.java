package com.example.agencyrest.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agencyId;

    private String name;
    private String password;

    public Agency() {
    }

    public Agency(Long agencyId, String name, String password) {
        this.agencyId = agencyId;
        this.name = name;
        this.password = password;
    }
}
