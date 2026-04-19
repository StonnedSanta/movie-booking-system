package model;

import service.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        BookingServiceImpl service = new BookingServiceImpl();

        Movie movie1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        
        Show show1 = new Show(1, movie1, "10:00 AM");
        service.addShow(show1);

        // User user = new User(101, "Anuj");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5; i++) {
            int userId = i;

            executor.submit(() -> {
                try {
                    User user = new User(userId, "User" + userId);
                    Booking b = service.bookTicket(user, 1, 7);
                    System.out.println("User" + userId + " booked: " + b);
                } catch (Exception e) {
                    System.out.println("User" + userId + " failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
    }
}