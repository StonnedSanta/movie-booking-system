package service;

import model.*;

public interface BookingService {

    Booking bookTicket(User user, int showId, int seatNumber);
    double calculateAmount(Movie movie);
    
}
