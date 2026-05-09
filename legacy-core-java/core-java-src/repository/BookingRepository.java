package repository;

import java.sql.*;

public class BookingRepository {
    
    private final String url = "jdbc:postgresql://localhost:5432/movie_booking";
    private final String user = "postgres";
    private final String password = "postgres";

    public String bookSeatWithIdempotency(int userId, int showId, int seatNumber, String requestId) {

        String checkSql = "SELECT status FROM bookings WHERE request_id = ?";

        String insertSql = """
        INSERT INTO bookings 
        (user_id, show_id, seat_number, status, request_id)
        VALUES (?, ?, ?, 'PENDING', ?)
        """;

        String confirmSql = """
        UPDATE bookings
        SET status = 'CONFIRMED'
        WHERE request_id = ?
        """;

        String lockSql = "SELECT id FROM shows WHERE id = ? FOR UPDATE";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

        conn.setAutoCommit(false); // start transaction

        // Check idempotency
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, requestId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                conn.rollback();
                return "Duplicate request detected -> " + rs.getString("status");
            }
        }

        // Lock row
        try (PreparedStatement lockStmt = conn.prepareStatement(lockSql)) {
            lockStmt.setInt(1, showId);
            lockStmt.executeQuery();
        }

        // Insert booking
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, showId);
            insertStmt.setInt(3, seatNumber);
            insertStmt.setString(4, requestId);
            insertStmt.executeUpdate();
        }

        // Payment
        boolean paymentSuccess = simulatePayment(userId);

        if (!paymentSuccess) {
            conn.rollback();
            return "Payment failed -> Booking rolled back";
        }

        // Confirm booking
        try (PreparedStatement confirmStmt = conn.prepareStatement(confirmSql)) {
            confirmStmt.setString(1, requestId);
            confirmStmt.executeUpdate();
        }

        conn.commit();
        return "Booking CONFIRMED";

    } catch (SQLException e) {
        return "Seat already booked!";
    }
}
    private boolean simulatePayment(int userId) {
        return userId != 3;
    }
}