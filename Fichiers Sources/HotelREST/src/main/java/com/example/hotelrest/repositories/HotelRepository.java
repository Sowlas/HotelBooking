package com.example.hotelrest.repositories;

import com.example.hotelrest.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findById(Long id);

    List<Hotel> findAll();
}

