package repository;

import java.sql.*;

public class BookingRepository {
    
    private final String url = "jdbc:postgresql://localhost:5432/movie_booking";
    private final String user = "postgres";
    private final String password = "postgres";

    public String bookSeatWithLock(int userId, int showId, int seatNumber) {

        String lockSql = "SELECT id FROM shows WHERE id = ? FOR UPDATE";

        String insertSql = """
        INSERT INTO bookings (user_id, show_id, seat_number, status)
        VALUES (?, ?, ?, 'PENDING')
        """;

        String confirmSql = """
        UPDATE bookings
        SET status = 'CONFIRMED'
        WHERE user_id = ? AND show_id = ? AND seat_number = ?
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            conn.setAutoCommit(false); // start transaction

            // lock show row
            try (PreparedStatement lockStmt = conn.prepareStatement(lockSql)) {
                lockStmt.setInt(1, showId);
                lockStmt.executeQuery();
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, showId);
            insertStmt.setInt(3, seatNumber);

            insertStmt.executeUpdate();

        }

        // simulate payment
        boolean paymentSuccess = simulatePayment(userId);

        if (!paymentSuccess) {
            conn.rollback(); //payment failed
            return "Payment failed -> Booking rolled back";
        }

        // payment success
        try (PreparedStatement confirmStmt = conn.prepareStatement(confirmSql)) {

            confirmStmt.setInt(1, userId);
            confirmStmt.setInt(2, showId);
            confirmStmt.setInt(3, seatNumber);

            confirmStmt.executeUpdate();
        }

        conn.commit(); // final commit

        return "Booking CONFIRMED";
    } catch (SQLException e) {
        return "Seat already booked!";
    }
}    
  
    public String bookSeatWithRetry(int userId, int showId, int seat_number) {
            
        int maxRetries = 3;
        long delay = 200;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {

            String result = bookSeatWithLock(userId, showId, seat_number);

            // terminal case
            if (result.equals("Booking CONFIRMED") ||
                result.equals("Payment failed -> Booking rolled back")) {
                    return result;
                }

            if (result.equals("Seat already booked!")) {

                if (attempt == maxRetries) {
                    return "Failed after retries";
                }

                System.out.println("User" + userId + 
                    " retrying... attempt " + attempt);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ignored) {}

                delay *= 2; // exponential backoff
            }
        }

        return "Failed after retires";

    }

    // payment simulation
        private boolean simulatePayment(int userId) {
            return userId != 3; // user3 fails
        }
}