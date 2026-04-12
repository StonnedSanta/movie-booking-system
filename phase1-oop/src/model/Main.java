package model;

import service.*;

public class Main {
    public static void main(String[] args) {

        BookingServiceImpl service = new BookingServiceImpl();

        Movie movie1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        service.addMovie(movie1);

        User user = new User(101, "Anuj");
        Booking booking = service.bookTicket(user, movie1, 5);
        
        System.out.println(booking);
    }
}