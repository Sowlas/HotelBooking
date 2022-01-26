package com.example.agencyrest;

import com.example.agencyrest.models.Agency;
import com.example.agencyrest.models.Hotel;
import com.example.agencyrest.repositories.AgencyRepository;
import com.example.agencyrest.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Data {
    public static final int NBAGENCY = 10;
    public static final int NBHOTEL = 20;
    @Autowired
    AgencyRepository agencyRepository;
    @Autowired
    HotelRepository hotelRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        for (int i = 1; i <= NBAGENCY; i++) {
            agencyRepository.save(randomAgency(i));
            for (int j = 1; j <= NBHOTEL; j++) {
                hotelRepository.save(randomHotel(i, j));
            }
        }

    }

    public Agency randomAgency(int i) {
        Agency agency = new Agency();
        agency.setName("Agence " + i);
        agency.setPassword("agence" + i);
        return agency;
    }

    public Hotel randomHotel(int i, int j) {
        Hotel hotel = new Hotel();
        hotel.setUri("http://localhost:8080/hotel/" + j);
        hotel.setAgencyId(i);
        return hotel;
    }
}
