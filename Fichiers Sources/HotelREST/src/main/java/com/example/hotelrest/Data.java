package com.example.hotelrest;

import com.example.hotelrest.models.*;
import com.example.hotelrest.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Data {
    public static final int NBHOTEL = 20;
    public static final int NBROOMS = NBHOTEL * 100;
    public static final int NBPARTNERS = 10;
    String img_path = "IMG/Hotel/";
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PartnerRepository partnerRepository;

    @SuppressWarnings("unused")
    private String[] cities = {"Montpellier", "Paris", "Nice", "Marseille", "Lyon"};

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        //INITIALISATION D HOTELS

        for (int i = 1; i <= NBHOTEL; i++) {
            hotelRepository.save(randomHotel(i));
        }
        for (int i = 1; i <= NBROOMS; i++) {
            roomRepository.save(randomRoom(i));
        }
        for (int i = 1; i <= NBPARTNERS; i++) {
            partnerRepository.save(randomPartner(i));
        }


        customerRepository.save(new Customer(null, "ClientA", "ClientA", "1234"));
        bookingRepository.save(new Booking(null, 2, 1, "2022-01-01", "2022-01-02"));

        customerRepository.save(new Customer(null, "ClientB", "ClientB", "5678"));
        bookingRepository.save(new Booking(null, 3, 2, "2022-01-01", "2022-01-02"));
        bookingRepository.save(new Booking(null, 3, 2, "2022-02-02", "2022-02-02"));

        customerRepository.save(new Customer(null, "ClientA", "ClientA", "9101"));
        bookingRepository.save(new Booking(null, 4, 3, "2022-01-01", "2022-01-02"));

    }

    public Room randomRoom(int i) {
        Room room = new Room();
        room.setName("Chambre " + i);
        room.setNbBeds((int) Math.floor(Math.random() * (4) + 1));
        room.setPrice((int) Math.floor(Math.random() * (400) + 50));
        room.setImgPath(img_path + "chambre" + (int) Math.floor(Math.random() * (4) + 1) + ".jpg");
        room.setHotelId((int) Math.floor(Math.random() * (NBHOTEL) + 1));
        return room;
    }

    public Hotel randomHotel(int i) {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel " + i);
        hotel.setNbStars((int) Math.floor(Math.random() * (5) + 1));
        hotel.setCity(cities[(int) Math.floor(Math.random() * (cities.length))]);
        return hotel;
    }


    public Partner randomPartner(int i) {
        Partner partner = new Partner();
        partner.setName("Agence " + i);
        partner.setPassword("agence" + i);
        partner.setRate((int) Math.floor(Math.random() * (100)));
        partner.setHotelId((int) Math.floor(Math.random() * (NBHOTEL) + 1));
        return partner;
    }


}