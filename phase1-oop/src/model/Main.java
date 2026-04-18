package model;

import service.*;

public class Main {
    public static void main(String[] args) {

        BookingServiceImpl service = new BookingServiceImpl();

        Movie movie1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        
        Show show1 = new Show(1, movie1, "10:00 AM");
        service.addShow(show1);

        User user = new User(101, "Anuj");

        Runnable task1 = () -> {
            try {
                Booking b = service.bookTicket(user, 2, 7);
                System.out.println("User1 booked: " + b);
            } catch (Exception e) {
                System.out.println("User1 failed: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                Booking b = service.bookTicket(user, 1, 9);
                System.out.println("User2 booked: " + b);
            } catch (Exception e) {
                System.out.println("User2 failed: " + e.getMessage());
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();
    }
}
