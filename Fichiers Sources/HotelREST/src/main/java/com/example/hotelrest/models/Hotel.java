package com.example.hotelrest.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String name;
    private int nbStars;
    private String city;

    public Hotel(Long id, String name, int nbStars, String city) {
        super();
        this.hotelId = id;
        this.name = name;
        this.nbStars = nbStars;
        this.city = city;
    }

    public Hotel() {
    }

}
