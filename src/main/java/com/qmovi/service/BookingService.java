package com.qmovi.service;

import com.qmovi.dto.BookingDTO;
import com.qmovi.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(BookingDTO bookingDTO);

    List<Booking> getAllBookings();
}