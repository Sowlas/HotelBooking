package com.example.hotelrest.repositories;

import com.example.hotelrest.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findById(Long id);

    List<Room> findAll();

    @Query(value = "SELECT * FROM room WHERE hotel_Id = :id", nativeQuery = true)
    List<Room> findRoomsOf(@Param(value = "id") long id);

}
