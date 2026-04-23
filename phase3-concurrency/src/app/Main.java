package app;

import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import model.BookingStatus;
import model.Movie;
import model.Show;
import model.User;

public class Main {
    public static void main(String[] args) {

        BookingServiceImpl service = new BookingServiceImpl();

        Movie movie1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        Show show1 = new Show(1, movie1, "10:00 AM");
        service.addShow(show1);

        PaymentService paymentService = new PaymentService();
        NotificationService notificationService = new NotificationService();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int userId = i;

            CompletableFuture<Void> f =
                CompletableFuture
                    .supplyAsync(() -> {
                        User user = new User(userId, "User" + userId);
                        return service.bookTicket(user, 1, 7);
                    })
                    .thenCompose(booking -> {

                        // parallel tasks
                        CompletableFuture<String> paymentFuture = 
                            paymentService.processPayment(booking);

                        CompletableFuture<String> notificationFuture =
                            notificationService.sendNotification(booking);
                        
                        // wait for both
                        return CompletableFuture.allOf(paymentFuture, notificationFuture)
                            .thenApply(v -> {
                            booking.setStatus(BookingStatus.CONFIRMED);
                            return "Payment + Notification done for bookingId = " + booking.getBookingId();
                        });    
                    })
                    .thenAccept(result -> {
                        System.out.println("User" + userId + ": " + result);
                    })
                    .exceptionally(ex -> {
                        System.out.println("User" + userId + " failed: " + ex.getMessage());
                        return null;
                    });    

        futures.add(f);
    }

        CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        ).join();
    }
}   