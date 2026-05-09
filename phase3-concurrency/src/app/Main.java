package app;

import repository.BookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {

        BookingRepository bookingRepo = new BookingRepository();

        System.out.println("\n--- Clean Setup ---");

        // use new seat every run
        int showId = 1;
        int seat = (int) (Math.random() * 100 + 1); //random seat

        System.out.println("Using seat: " + seat);

        // Idempotency test
        System.out.println("\n--- Idempotency Test ---");
        String requestId = "REQ-123"; //same request

        System.out.println(
            bookingRepo.bookSeatWithIdempotency(1, showId, seat, requestId)
        );

        System.out.println(
            bookingRepo.bookSeatWithIdempotency(1, showId, seat, requestId)
        );

        System.out.println(
            bookingRepo.bookSeatWithIdempotency(1, showId, seat, requestId)
        );

        // Concurrency test
        System.out.println("\n--- Concurrency Test ---");
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {

            int userId = i;
            int finalSeat = seat + 1; // same seat -> contention

            String uniqueRequestId = "REQ-" + userId + "-" + System.nanoTime();

            CompletableFuture<Void> f =
                CompletableFuture.runAsync(() -> {
                    String result = bookingRepo.bookSeatWithIdempotency(
                        userId, showId, finalSeat, uniqueRequestId
                    );
                    System.out.println("User" + userId + ": " + result);
                });

            futures.add(f);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        System.out.println("\n--- Test Completed ---");
    }
}