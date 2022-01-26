package com.example.hotelrest.services;

import com.example.hotelrest.models.Booking;
import com.example.hotelrest.models.Room;
import com.example.hotelrest.repositories.BookingRepository;
import com.example.hotelrest.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public HotelService(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public ArrayList<Room> fetchRooms(long id,
                                      String start, String end, int minPrice,
                                      int maxPrice, int nbBeds) {

        ArrayList<Room> res = new ArrayList<>();

        for (Room c : roomRepository.findRoomsOf(id)) {
            if ((c.getPrice() >= minPrice) && (c.getPrice() <= maxPrice) && (c.getNbBeds() >= nbBeds)) {

                int startB = Integer.parseInt(start.replace("-", ""));
                int endB = Integer.parseInt(end.replace("-", ""));
                boolean available = true;

                List<Booking> bookings = bookingRepository.getBookingsOfRoom(c.getRoomId());
                for (int i = 0; i < bookings.size(); i++) {
                    int startDate = Integer.parseInt(bookings.get(i).getStartDate().replace("-", ""));
                    int endDate = Integer.parseInt(bookings.get(i).getEndDate().replace("-", ""));

                    if ((endB >= startDate && endB <= endDate) || (startB <= endDate && startB >= startDate)) {
                        available = false;
                    }
                }
                if (available) {
                    res.add(c);
                }
            }
        }
        return res;
    }
}
