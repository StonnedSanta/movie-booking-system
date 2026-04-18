package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingServiceImpl implements BookingService {

    private Map<Integer, Show> shows = new HashMap<>();
    private List<Booking> bookings = new ArrayList<>();

    public void addShow(Show show) {
        shows.put(show.getShowId(), show);
    }
    

    @Override
    public Booking bookTicket(User user, int showId, int seatNumber) {

        Show show = shows.get(showId);

        if(show == null) {
            throw new IllegalArgumentException("Invalid show");
        }

        if(!show.tryBookSeat(seatNumber)) {
            throw new IllegalArgumentException("Seat already booked");
        }

        show.bookedSeat(seatNumber);

        double amount = calculateAmount(show.getMovie());
        Booking booking = new Booking(bookings.size() + 1, user, show, seatNumber, amount);

        bookings.add(booking);

        return booking;
    }

    @Override
    public double calculateAmount(Movie movie) {
        return 200.0; //fixed as of now
    }

}
