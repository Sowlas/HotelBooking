package com.example.trivago.distantRESTmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.File;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

    private Long roomId;
    private String name;
    private int nbBeds;
    private int price;
    private File img;
    private int hotelId;

    public Room() {
    }

    public Room(String name, int nbBeds, int price, File img, int hotelId) {
        super();
        this.name = name;
        this.nbBeds = nbBeds;
        this.price = price;
        this.img = img;
        this.hotelId = hotelId;
    }
}
