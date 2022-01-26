package com.example.trivago.distantRESTmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistantHotel {

    private Long distantHotelId;
    private String name;
    private int nbStars;
    private String city;

    public DistantHotel(String name, int nbStars, String city) {
        this.name = name;
        this.nbStars = nbStars;
        this.city = city;
    }

    public DistantHotel() {
    }

}
