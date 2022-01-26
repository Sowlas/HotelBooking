package com.example.agencyrest.repositories;

import com.example.agencyrest.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findById(Long id);

    List<Hotel> findAll();


    @Query(value = "SELECT * FROM hotel WHERE agency_id = :id", nativeQuery = true)
    List<Hotel> findHotelsOf(@Param(value = "id") long id);
}
