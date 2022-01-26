package com.example.agencyrest.hotelRESTmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer {

    private DistantHotel hotel;
    private Room room;
    private int price;

    public Offer(DistantHotel hotel, Room room, Integer price) {
        super();
        this.hotel = hotel;
        this.room = room;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Offre de  \"" + hotel.getName() + "\" a \"" + hotel.getCity() + "\": " + room.getName() + " " + "Prix: " + price + "e";
    }


}
