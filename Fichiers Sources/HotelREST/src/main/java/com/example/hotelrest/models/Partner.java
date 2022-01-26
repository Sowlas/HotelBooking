package com.example.hotelrest.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerId;
    private String name;
    private String password;
    private int rate;
    private int hotelId;

    public Partner() {
    }

    public Partner(Long partnerId, String name, String password, int rate, int hotelId) {
        super();
        this.partnerId = partnerId;
        this.name = name;
        this.password = password;
        this.rate = rate;
        this.hotelId = hotelId;
    }
}
