package service;

import model.*;
import java.util.List;

public interface BookingService {

    List<Movie> getAvailableMovies();
    List<Movie> getMoviesByGenre(String genre);
    Booking bookTicket(User user, Movie movie, int seatNumber);
    double calculateAmount(Movie movie);
    
}
