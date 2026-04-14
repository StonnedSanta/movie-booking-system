package model;

import service.*;

public class Main {
    public static void main(String[] args) {

        BookingServiceImpl service = new BookingServiceImpl();

        Movie movie1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        
        Show show1 = new Show(1, movie1, "10:00 AM");
        Show show2 = new Show(2, movie1, "08:00 PM");

        service.addShow(show1);
        service.addShow(show2);

        User user = new User(101, "Anuj");

        Booking booking1 = service.bookTicket(user, 1, 5);
        
        System.out.println(booking1);

        try{
            Booking booking2 = service.bookTicket(user, 1, 5);
            System.out.println(booking2); 
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        } 

        System.out.println("Available Seats: " + show1.getAvailableSeats(10));

        Booking booking3 = service.bookTicket(user, 2, 5);
        System.out.println("Different show bookings: " + booking3);
    }
}