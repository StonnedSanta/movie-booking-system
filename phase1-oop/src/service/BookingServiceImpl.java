package service;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private List<Movie> movies = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    // add movie (helper method)
    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    @Override
    public List<Movie> getAvailableMovies() {
        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> result = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                result.add(movie);
            }
        }
        return result;
    }

    @Override
    public Booking bookTicket(User user, Movie movie, int seatNumber) {
        if(!movie.isSeatAvilable(seatNumber)) {
            throw new IllegalArgumentException("Seat already booked");
        }

        movie.bookSeat(seatNumber);

        double amount = calculateAmount(movie);
        Booking booking = new Booking(bookings.size() + 1, user, movie, seatNumber, amount);

        bookings.add(booking);

        return booking;
    }

    @Override
    public double calculateAmount(Movie movie) {
        return 200.0; //fixed as of now
    }
}
