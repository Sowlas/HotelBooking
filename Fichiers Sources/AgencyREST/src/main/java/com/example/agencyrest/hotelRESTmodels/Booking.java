package com.example.agencyrest.hotelRESTmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

    private Long bookingId;
    private int roomId;
    private int customerId;
    private String startDate;
    private String endDate;

    public Booking() {
    }

    public Booking(int roomId, int customerId, String startDate, String endDate) {
        super();
        this.roomId = roomId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
