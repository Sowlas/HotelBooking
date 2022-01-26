package com.example.agencyrest.models;

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

    private String uri;
    private int agencyId;

    public Hotel() {
    }

    public Hotel(Long hotelId, String uri, int agencyId) {
        super();
        this.hotelId = hotelId;
        this.uri = uri;
        this.agencyId = agencyId;
    }

}
