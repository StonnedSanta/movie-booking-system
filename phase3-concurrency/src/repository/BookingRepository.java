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
            // fake payment logic
        private boolean simulatePayment(int userId) {
        
            // Example: fail for user 3
            return userId != 3;
    }
}