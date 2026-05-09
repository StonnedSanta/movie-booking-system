package model;

public class Booking {
    
    private int bookingId;
    private User user;
    private Show show;
    private int seatNumber;
    private double amount;
    private BookingStatus status;

    public Booking(int bookingId, User user, Show show, int seatNumber, double amount) {
        if(seatNumber <= 0) {
            throw new IllegalArgumentException("Seat number must be positive");
        }

        this.bookingId = bookingId;
        this.user = user;
        this.show = show;
        this.seatNumber = seatNumber;
        this.amount = amount;
        
        this.status = BookingStatus.PENDING;
    }

    public int getBookingId() {
        return bookingId;
    }
    
    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getAmount() {
        return amount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", show=" + show +
                ", seatNumber=" + seatNumber +
                ", amount=" + amount +
                '}';
        }
    
}
