package model;

import java.util.HashSet;
import java.util.Set;

public class Movie {
    private int id; // encapsulation(s)
    private String name;
    private String genre;
    private double rating;

    private Set<Integer> bookedSeats = new HashSet<>();

    public Movie(int id, String name, String genre, double rating) {
        this.id = id; // this keyword --> refers the current object(s)
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if(rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Enter values between 0 to 5");
        }
        this.rating = rating;
    }

    public boolean isSeatAvilable(int seatNumber) {
        return !bookedSeats.contains(seatNumber);
    }

    public void bookSeat(int seatNumber) {
        bookedSeats.add(seatNumber);
    }

    public Set<Integer> getAvailableSeats(int totalSeats) {
        Set<Integer> availableSeats = new HashSet<>();

        for (int i = 1; i <= totalSeats; i++) {
            if (!bookedSeats.contains(i)) {
                availableSeats.add(i);
            }
        }

        return availableSeats;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}';
    }
}
