package com.example.trivago.distantRESTmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer {

    private DistantHotel hotel;
    private Room room;
    private int price;
    private DistantAgency distantAgency;

    public Offer(DistantHotel hotel, Room room, Integer price) {
        super();
        this.hotel = hotel;
        this.room = room;
        this.price = price;
    }


    @Override
    public String toString() {
        return "Offre de  \"" + hotel.getName() + "\" a \"" + hotel.getCity() + "\": " + room.getName() + " " + "Prix: " + price + "e " + "(" +
                distantAgency.getName() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return getPrice() == offer.getPrice() && Objects.equals(getHotel(), offer.getHotel()) && Objects.equals(getRoom(), offer.getRoom()) && Objects.equals(getDistantAgency(), offer.getDistantAgency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHotel(), getRoom(), getPrice(), getDistantAgency());
    }
}
