package app;

import service.*;
import repository.MovieRepository;
import repository.ShowRepository;
import repository.UserRepository;
import repository.BookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import model.Movie;
import model.Show;
import model.User;

public class Main {
    public static void main(String[] args) {

        // DB Part
        UserRepository userRepo = new UserRepository();
        MovieRepository movieRepo = new MovieRepository();
        ShowRepository showRepo = new ShowRepository();
        BookingRepository bookingRepo = new BookingRepository();

        // Insert users into DB
        userRepo.addUser("User1", "user1@mail.com");
        userRepo.addUser("User2", "user2@mail.com");
        userRepo.addUser("User3", "user3@mail.com");
        userRepo.addUser("User4", "user4@mail.com");
        userRepo.addUser("User5", "user5@mail.com");

        System.out.println("\n--- Users from DB ---");
        System.out.println(userRepo.getAllUsers());

        // Movies (DB + In-Memory bridge)
        Movie m1 = new Movie(1, "Inception", "Sci Fi", 4.8);
        Movie m2 = new Movie(2, "Interstellar", "Sci Fi", 4.7);
        Movie m3 = new Movie(3, "Titanic", "Romance", 4.5);
        Movie m4 = new Movie(4, "Avengers", "Action", 4.6);

        // Save to DB
        movieRepo.addMovie(m1);
        movieRepo.addMovie(m2);
        movieRepo.addMovie(m3);
        movieRepo.addMovie(m4);

        System.out.println("\n=== Movies from DB ---");
        System.out.println(movieRepo.getAllMovies());

        // Shows DB
        showRepo.addShow(1, "10:00 AM");
        showRepo.addShow(2, "06:00 PM");

        System.out.println("--- Shows from DB ---");
        System.out.println(showRepo.getAllShows());

        BookingServiceImpl service = new BookingServiceImpl();

        // Add movies to in-memory for existing logic
        service.addMovie(m1);
        service.addMovie(m2);
        service.addMovie(m3);
        service.addMovie(m4);

        service.addShow(new Show(1, m1, "10:00 AM"));

        // Concurrent booking test
        System.out.println("\n--- Concurrent Booking Test ---");

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int userId = i;

            CompletableFuture<Void> f =
                    CompletableFuture.runAsync(() -> {
                        String result = bookingRepo.bookSeat(userId, 1, 1);
                        System.out.println("User" + userId + ": " + result);
                    });

                futures.add(f);
        }

        CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        ).join();

        System.out.println("\n--- Test Completed ---");
    }

}