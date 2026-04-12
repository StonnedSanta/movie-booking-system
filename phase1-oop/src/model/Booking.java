package model;

public class Booking {
    
    private int bookingId;
    private User user;
    private Movie movie;
    private int seatNumber;
    private double amount;

    public Booking(int bookingId, User user, Movie movie, int seatNumber, double amount) {
        if(seatNumber <= 0) {
            throw new IllegalArgumentException("Seat number must be positive");
        }

        this.bookingId = bookingId;
        this.user = user;
        this.movie = movie;
        this.seatNumber = seatNumber;
        this.amount = amount;
    }

    public int getBookingId() {
        return bookingId;
    }
    
    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", movie=" + movie +
                ", seatNumber=" + seatNumber +
                ", amount=" + amount +
                '}';
        }
    
}
