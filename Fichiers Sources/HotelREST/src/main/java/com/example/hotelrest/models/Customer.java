package com.example.hotelrest.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;
    private String surname;
    private String card;

    public Customer() {
    }

    public Customer(Long customerId, String name, String surname, String card) {
        super();
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
        this.card = card;
    }
}
