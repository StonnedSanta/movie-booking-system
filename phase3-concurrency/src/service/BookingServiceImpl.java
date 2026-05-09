package service;

import model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private Map<Integer, Show> shows = new HashMap<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getAvailableMovies() {
        return movies;
    }

    // Java 8 Stream - filter
    public List<Movie> getMoviesByGenre(String genre) {
        return movies.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

    // Java 8 Stream - map
    public List<String> getMovieNames() {
        return movies.stream()
                .map(Movie::getName)
                .toList();
    }

    // Java 8 Stream - sorting
    public List<Movie> getMoviesSortedByRating() {
        return movies.stream()
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                .toList();
    }

    // Java 8 Stream - combined pipeline
    public List<String> getTopRatedMovieNamesByGenre(String genre) {
        return movies.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                .map(Movie::getName)
                .toList();
    }

    // Java 8 Stream - Optional
    public Optional<Movie> getTopRatedMovie() {
        return movies.stream()
                .max(Comparator.comparingDouble(Movie::getRating));
    }

    // Java 8 Stream - grouping
    public Map<String, List<Movie>> groupMoviesByGenre() {
        return movies.stream()
                .collect(Collectors.groupingBy(Movie::getGenre));
    }

    public Map<String, Long> countMoviesByGenre() {
        return movies.stream()
                .collect(Collectors.groupingBy(
                        Movie::getGenre,
                        Collectors.counting()
                ));
    }

    public Map<String, List<String>> getMovieNamesByGenre() {
        return movies.stream()
                .collect(Collectors.groupingBy(
                        Movie::getGenre,
                        Collectors.mapping(Movie::getName, Collectors.toList())
                ));
    }

    public Map<Boolean, List<Movie>> partitionByRating() {
        return movies.stream()
                .collect(Collectors.partitioningBy(
                        m -> m.getRating() > 4.5
                ));
    }

    // Show management
     public void addShow(Show show) {
        shows.put(show.getShowId(), show);
    }

    // Booking logic
    @Override
    public Booking bookTicket(User user, int showId, int seatNumber) {

        Show show = shows.get(showId);

        if(show == null) {
            throw new IllegalArgumentException("Invalid show");
        }

        if(!show.tryBookSeat(seatNumber)) {
            throw new IllegalArgumentException("Seat already booked");
        }

        show.bookedSeat(seatNumber);

        double amount = calculateAmount(show.getMovie());
        Booking booking = new Booking(bookings.size() + 1, user, show, seatNumber, amount);

        bookings.add(booking);

        return booking;
    }

    @Override
    public double calculateAmount(Movie movie) {
        return 200.0; //fixed as of now
    }

    // Async flow (book + payment + notification)
    public CompletableFuture<String> bookWithPaymentAndNotification (
        User user,
        int showId,
        int seatNumber,
        PaymentService paymentService,
        NotificationService notificationService
    ) {

        return CompletableFuture
                .supplyAsync(() -> bookTicket(user, showId, seatNumber))
                .thenCompose(booking -> {

                    CompletableFuture<String> paymentFuture =
                        paymentService.processPaymentWithRetry(booking, 3);

                    CompletableFuture<String> notifiationFuture =
                        notificationService.sendNotification(booking)
                            .exceptionally(ex -> {
                                System.out.println("Notification failed for bookingId = " + booking.getBookingId());
                                return "FAILED";
                            });

                    return paymentFuture
                            .thenCompose(paymentResult -> 
                                CompletableFuture.allOf(notifiationFuture)
                                    .thenApply(v -> {
                                        booking.setStatus(BookingStatus.CONFIRMED);
                                        return "Booking CONFIRMED for bookingId = " + booking.getBookingId();
                                    })
                            )
                        .exceptionally(ex -> {
                            booking.setStatus(BookingStatus.FAILED);
                            booking.getShow().releaseSeat(booking.getSeatNumber());

                            System.out.println("Rollback done for bookingId = " + booking.getBookingId());

                            throw new RuntimeException(
                                ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()
                            );
                        });

                });
    }


}
