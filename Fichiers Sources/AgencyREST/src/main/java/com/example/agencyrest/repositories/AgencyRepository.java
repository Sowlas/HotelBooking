package com.example.agencyrest.repositories;

import com.example.agencyrest.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AgencyRepository extends JpaRepository<Agency, Long> {

    Optional<Agency> findById(Long id);

    List<Agency> findAll();
}
