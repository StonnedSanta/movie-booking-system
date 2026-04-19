package model;

import service.*;

import java.lang.invoke.CallSite;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {

        BookingServiceImpl service = new BookingServiceImpl();

        Movie movie1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        
        Show show1 = new Show(1, movie1, "10:00 AM");
        service.addShow(show1);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5; i++) {
            int userId = i;

            Callable<Booking> task = () -> {
                User user = new User(userId, "User" + userId);
                return service.bookTicket(user, 1, 7);
            };

            Future<Booking> future = executor.submit(task);

            try {
                Booking booking = future.get();
                System.out.println("User" + userId + " booked: " + booking);
            } catch (Exception e) {
                System.out.println("User" + userId + " failed: " + e.getCause().getMessage());
            }

        }

        executor.shutdown();
    }
}