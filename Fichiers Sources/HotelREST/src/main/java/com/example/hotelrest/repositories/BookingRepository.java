package com.example.hotelrest.repositories;

import com.example.hotelrest.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findById(Long id);

    List<Booking> findAll();

    @Query(value = "SELECT * FROM booking WHERE room_Id = :id", nativeQuery = true)
    List<Booking> getBookingsOfRoom(@Param(value = "id") long id);
}
