package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class BookingServiceImpl implements BookingService {

    private Map<Integer, Show> shows = new HashMap<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();

    public void addShow(Show show) {
        shows.put(show.getShowId(), show);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movies.stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

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

}
