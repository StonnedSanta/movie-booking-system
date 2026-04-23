package service;

import java.util.concurrent.CompletableFuture;

import model.Booking;

public class PaymentService {

    public CompletableFuture<String> processPayment(Booking booking) {
        return CompletableFuture.supplyAsync(() -> {

            // simulate random failure
            if (Math.random() < 0.3) {
                throw new RuntimeException("Payment Failed");
            }

            try { Thread.sleep(500); } catch (Exception e) {}
            
            return "SUCCESS for bookindId = " + booking.getBookingId();
        });
    }
    
}
