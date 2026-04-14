package service;

import model.*;
import java.util.List;

public interface BookingService {

    Booking bookTicket(User user, int showId, int seatNumber);
    double calculateAmount(Movie movie);
    
}
