package com.example.hotelrest.controllers;


import com.example.hotelrest.models.Offer;
import com.example.hotelrest.models.Partner;
import com.example.hotelrest.models.Room;
import com.example.hotelrest.repositories.*;
import com.example.hotelrest.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HotelController {

    @Autowired
    public HotelRepository hotelRepository;
    @Autowired
    public HotelService hotelService;
    @Autowired
    public RoomRepository roomRepository;
    @Autowired
    public BookingRepository bookingRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public PartnerRepository partnerRepository;


    @GetMapping("/hotel/{id}/{agency}:{password}-from={start},to={end},minPrice={min},maxPrice={max},nbBeds={nbBeds}")
    public ArrayList<Offer> fetchOffers(@PathVariable long id, @PathVariable String agency, @PathVariable String password,
                                        @PathVariable String start, @PathVariable String end, @PathVariable int min,
                                        @PathVariable int max, @PathVariable int nbBeds) {
        ArrayList<Offer> res = new ArrayList<>();

        int rate = 0;
        for (Partner p : partnerRepository.findPartnersOf(id)) {
            if (p.getName().equalsIgnoreCase(agency) && p.getPassword().equalsIgnoreCase(password)) {
                rate = p.getRate();
            }
        }

        ArrayList<Room> tmp = hotelService.fetchRooms(id, start, end, min, max, nbBeds);
        for (Room r : tmp) {
            Offer o = new Offer(hotelRepository.findById(id).get(), r, (r.getPrice() - (r.getPrice() * rate/100)));
            res.add(o);
        }

        return res;
    }

}
