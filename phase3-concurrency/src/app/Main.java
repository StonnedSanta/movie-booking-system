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

        // Add movies
        service.addMovie(new Movie(1, "Inception", "Sci Fi", 4.8));
        service.addMovie(new Movie(2, "Interstellar", "Sci Fi", 4.7));
        service.addMovie(new Movie(3, "Titanic", "Romance", 4.5));
        service.addMovie(new Movie(4, "Avengers", "Action", 4.6));

        // Add show
        Show show1 = new Show(1, service.getAvailableMovies().get(0), "10:00 AM");
        service.addShow(show1);

        // Test streams
        System.out.println("\n--- Movie Names ---");
        System.out.println(service.getMovieNames());

        System.out.println("\n--- Sorted Movies by Rating ---");
        System.out.println(service.getMoviesSortedByRating());

        System.out.println("\n--- Top Sci-Fi Movies ---");
        System.out.println(service.getTopRatedMovieNamesByGenre("Sci Fi"));

        System.out.println("\n--- Top Rated Movie ---");
        service.getTopRatedMovie()
                .ifPresent(movie -> System.out.println(movie.getName()));

        // Async booking flow
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