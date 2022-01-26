package com.example.agencyrest.controllers;

import com.example.agencyrest.hotelRESTmodels.DistantHotel;
import com.example.agencyrest.hotelRESTmodels.Offer;
import com.example.agencyrest.models.Agency;
import com.example.agencyrest.models.Hotel;
import com.example.agencyrest.repositories.AgencyRepository;
import com.example.agencyrest.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class AgencyController {

    @Autowired
    public AgencyRepository agencyRepository;
    @Autowired
    public HotelRepository hotelRepository;
    @Autowired
    RestTemplate proxy;

    // Agency
    @GetMapping("/agency")
    public List<Agency> getAgencies() {
        return agencyRepository.findAll();
    }

    @GetMapping("/agency/{id}")
    public Optional<Agency> getAgency(@PathVariable long id) {
        return agencyRepository.findById(id);
    }


    // Hotel
    @GetMapping("/hotel")
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/hotel/{id}")
    public Optional<Hotel> getHotel(@PathVariable long id) {
        return hotelRepository.findById(id);
    }

    @GetMapping("/agency/{id}/hotel")
    public List<Hotel> getHotelsOf(@PathVariable long id) {
        return hotelRepository.findHotelsOf(id);
    }

    @GetMapping("/agency/{id}/lookup/city={city},nbStars={nbStars},from={start},to={end},minPrice={min},maxPrice={max},nbBeds={nbBeds}")
    public List<Offer> lookup(@PathVariable long id, @PathVariable String city, @PathVariable int nbStars,
                              @PathVariable String start, @PathVariable String end, @PathVariable int min,
                              @PathVariable int max, @PathVariable int nbBeds) {

        List<Offer> res = new ArrayList<>();
        Optional<Agency> agency = getAgency(id);

        List<Hotel> hotels = getHotelsOf(id);
        for (Hotel h : hotels) {
            DistantHotel hot = proxy.getForObject(h.getUri(), DistantHotel.class);
            if ((hot.getNbStars() == nbStars) && hot.getCity().equalsIgnoreCase(city)) {

                String uri = h.getUri() + "/" + agency.get().getName() + ":" + agency.get().getPassword()
                        + "-from=" + start + ",to=" + end + ",minPrice=" + min + ",maxPrice=" + max + ",nbBeds=" + nbBeds;
                Offer[] offers = proxy.getForObject(uri, Offer[].class);
                res.addAll(Arrays.asList(offers));
            }
        }
        return res;
    }


}
