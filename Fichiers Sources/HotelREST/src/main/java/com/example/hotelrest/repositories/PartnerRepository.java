package com.example.hotelrest.repositories;

import com.example.hotelrest.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Optional<Partner> findById(Long id);

    List<Partner> findAll();

    @Query(value = "SELECT * FROM partner WHERE hotel_Id = :id", nativeQuery = true)
    List<Partner> findPartnersOf(@Param(value = "id") long id);


}
