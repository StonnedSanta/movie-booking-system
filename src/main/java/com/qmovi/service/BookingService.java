package com.qmovi.service;

import com.qmovi.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(Booking booking);

    List<Booking> getAllBookings();
}