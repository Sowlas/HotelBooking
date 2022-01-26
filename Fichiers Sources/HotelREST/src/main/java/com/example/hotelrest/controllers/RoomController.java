package com.example.hotelrest.controllers;

import com.example.hotelrest.models.*;
import com.example.hotelrest.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoomController {

    @Autowired
    public HotelRepository hotelRepository;
    @Autowired
    public RoomRepository roomRepository;
    @Autowired
    public BookingRepository bookingRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public PartnerRepository partnerRepository;


    @GetMapping("/hotel")
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("hotel/{id}")
    public Optional<Hotel> getHotel(@PathVariable long id) {
        return hotelRepository.findById(id);
    }


    // Rooms
    @GetMapping("/room")
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/room/{id}")
    public Optional<Room> getRoom(@PathVariable long id) {
        return roomRepository.findById(id);
    }

    @GetMapping("hotel/{id}/room")
    public List<Room> getRoomsOf(@PathVariable long id) {
        return roomRepository.findRoomsOf(id);
    }


    // Bookings
    @GetMapping("/booking")
    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/booking/{id}")
    public Optional<Booking> getBooking(@PathVariable long id) {
        return bookingRepository.findById(id);
    }

    @GetMapping("/room/{id}/booking")
    public List<Booking> getBookingsOf(@PathVariable long id) {
        return bookingRepository.getBookingsOfRoom(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/booking")
    public Booking addBooking(@RequestBody Booking booking) {
        return bookingRepository.save(booking);
    }

    @PostMapping("/room/{roomId}/customer={customerId}/start={start}/end={end}")
    public void addBooking(@PathVariable int roomId,
                           @PathVariable int customerId,
                           @PathVariable String start,
                           @PathVariable String end) {
        Booking booking = new Booking(null, roomId, customerId, start, end);
        bookingRepository.save(booking);
    }


    // Customer
    @GetMapping("/customer")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    public Optional<Customer> getCustomer(@PathVariable long id) {
        return customerRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customer")
    public Customer addCustomer(@RequestBody Customer cli) {
        return customerRepository.save(cli);
    }

    @PostMapping("/customer/signup/{name}:{surname}:{card}")
    public void addCustomer(@PathVariable String name,
                            @PathVariable String surname,
                            @PathVariable String card) {
        Customer c = new Customer(null, name, surname, card);
        customerRepository.save(c);
    }


    // Partner
    @GetMapping("/partner")
    public List<Partner> getPartners() {
        return partnerRepository.findAll();
    }

    @GetMapping("/partner/{id}")
    public Optional<Partner> getPartner(@PathVariable long id) {
        return partnerRepository.findById(id);
    }

    @GetMapping("/hotel/{id}/partner")
    public List<Partner> getPartnersOf(@PathVariable long id) {
        return partnerRepository.findPartnersOf(id);
    }
}
