package com.example.hotelrest.models;

import lombok.Data;

@Data
public class Offer {

    private Hotel hotel;
    private Room room;
    private Integer price;

    public Offer(Hotel hotel, Room room, Integer prix) {
        super();
        this.hotel = hotel;
        this.room = room;
        this.price = prix;
    }

}
