package com.example.trivago.repositories;

import com.example.trivago.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AgencyRepository extends JpaRepository<Agency, Long> {

    Optional<Agency> findById(Long id);

    List<Agency> findAll();

    @Query(value = "SELECT * FROM agency WHERE uri = :uri", nativeQuery = true)
    Optional<Agency> findByUri(@Param(value = "uri") String uri);

}
