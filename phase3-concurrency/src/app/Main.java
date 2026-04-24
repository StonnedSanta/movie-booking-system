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

            User user = new User(userId, "User" + userId);

            CompletableFuture<Void> f =
                service.bookWithPaymentAndNotification(
                    user, 
                    1, 
                    userId, 
                    paymentService, 
                    notificationService
                )
                .thenAccept(result -> System.out.println("User" + userId + ": " + result))
                .exceptionally(ex -> {
                    System.out.println("User" + userId + " failed: " + ex.getCause().getMessage());
                    return null;
                });
                
        futures.add(f);
    }

        CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        ).join();
    }
}   