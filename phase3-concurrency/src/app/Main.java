package app;

import service.*;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.Movie;
import model.Show;
import model.User;

public class Main {
    public static void main(String[] args) {

        BookingServiceImpl service = new BookingServiceImpl();

        Movie movie1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        
        Show show1 = new Show(1, movie1, "10:00 AM");
        service.addShow(show1);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int userId = i;

            CompletableFuture<Void> f =
                CompletableFuture
                    .supplyAsync(() -> {
                        User user = new User(userId, "User" + userId);
                        return service.bookTicket(user, 1, 7);
                    })
                    .thenAccept(b -> System.out.println("User" + userId + " booked: " + b))
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