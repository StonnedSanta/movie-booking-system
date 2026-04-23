package service;

import java.util.concurrent.CompletableFuture;

import model.Booking;

public class NotificationService {
    
    public CompletableFuture<String> sendNotification(Booking booking) {
        return CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(300); } catch (Exception e) {}
            return "Notification sent for bookingId = " + booking.getBookingId();
        });
    }
}
