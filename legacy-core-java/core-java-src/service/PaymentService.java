package service;

import java.util.concurrent.CompletableFuture;

import model.Booking;

public class PaymentService {

    public CompletableFuture<String> processPaymentWithRetry(Booking booking, int maxRetries) {
        return CompletableFuture.supplyAsync(() -> {
            int attempt = 0;

            while (attempt < maxRetries) {
                try {
                    attempt++;
                    
                    // simulate random failure
                    if (Math.random() < 0.3) {
                    throw new RuntimeException("Payment Failed");
                    }

                    return "SUCCESS for bookingId= " + booking.getBookingId()
                            + "(attempt " + attempt + ")";
                } catch (Exception e) {
                    if (attempt == maxRetries) {
                        throw new RuntimeException("Payment failed after " + maxRetries + " attempts");
                    }

                    System.out.println("Retrying payment for bookingId= "
                            + booking.getBookingId() + " (attempt " + attempt + ")");

                }
            }

                return "FAILED";

        });
    }
    
}
