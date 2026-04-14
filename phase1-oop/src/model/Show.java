package model;

import java.util.HashSet;
import java.util.Set;

public class Show {

    private int showId;
    private Movie movie;
    private String showTime;
    private Set<Integer> bookedSeats = new HashSet<>();

    public Show(int showId, Movie movie, String showTime) {
        this.showId = showId;
        this.movie = movie;
        this.showTime = showTime;
    }

    public int getShowId() {
        return showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getShowTime() {
        return showTime;
    }

    public boolean isSeatAvailable(int seatNumber) {
        return !bookedSeats.contains(seatNumber);
    }

    public void bookedSeat(int seatNumber) {
        bookedSeats.add(seatNumber);
    }

    public Set<Integer> getAvailableSeats(int totalSeats) {

        Set<Integer> availableSeats = new HashSet<>();

        for (int i =1; i <= totalSeats; i++) {
            if (!bookedSeats.contains(i)) {
                availableSeats.add(i);
            }
        }
        return availableSeats;
    }
    
    @Override
    public String toString() {
        return "Show{" +
                "showId=" + showId +
                ", movie=" + movie.getName() +
                ", showTime='" + showTime + '\'' +
                '}';
        }

}
