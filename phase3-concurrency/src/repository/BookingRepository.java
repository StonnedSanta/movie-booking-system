package repository;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

public class BookingRepository {
    
    private final String url = "jdbc:postgresql://localhost:5432/movie_booking";
    private final String user = "postgres";
    private final String password = "postgres";

    public String bookSeat(int userId, int showId, int seatNumber) {

        String sql = """
        INSERT INTO bookings (user_id, show_id, seat_number, status)
        VALUES (?, ?, ?, 'CONFIRMED')
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, showId);
            stmt.setInt(3, seatNumber);

            stmt.executeUpdate();

            return "Booking CONFIRMED";
        
            } catch (SQLException e) {
                return "Seat already booked";
        }
    }
}
