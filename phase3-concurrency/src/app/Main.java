package app;

import service.*;
import repository.MovieRepository;
import repository.ShowRepository;
import repository.UserRepository;
import repository.BookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import model.Booking;
import model.Movie;
import model.Show;
import model.User;

public class Main {
    public static void main(String[] args) {

        // DB part
        UserRepository userRepo = new UserRepository();
        MovieRepository movieRepo = new MovieRepository();
        ShowRepository showRepo = new ShowRepository();
        BookingRepository bookingRepo = new BookingRepository();

        // setup data
        userRepo.addUser("User1", "user1@mail.com");
        userRepo.addUser("User2", "user2@mail.com");
        userRepo.addUser("User3", "user3@mail.com");
        userRepo.addUser("User4", "user4@mail.com");
        userRepo.addUser("User5", "user5@mail.com");

        movieRepo.addMovie(new model.Movie(0, "Inception", "Sci Fi", 4.8));
        movieRepo.addMovie(new model.Movie(0, "Interstellar", "Sci Fi", 4.7));

        showRepo.addShow(1, "10:00 AM");

        // row locking booking + retry test
        System.out.println("\n--- Row Locking + Retry Test ---");

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int userId = i;

            CompletableFuture<Void> f =
                    CompletableFuture.runAsync(() -> {
                        String result = bookingRepo.bookSeatWithRetry(userId, 1, userId);
                        System.out.println("User" + userId + ": " + result);
                    });

                futures.add(f);
        }

        CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        ).join();

        System.out.println("--- Test Completed ---");
        
    }

}