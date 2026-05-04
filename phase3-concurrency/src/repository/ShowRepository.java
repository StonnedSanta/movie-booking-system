package repository;

import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;

public class ShowRepository {

    private final String url = "jdbc:postgresql://localhost:5432/movie_booking";
    private final String user = "postgres";
    private final String password = "postgres";

    public void addShow(int movieId, String showTime) {
        String sql = "INSERT INTO shows (movie_id, show_time) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, movieId);
                stmt.setString(2, showTime);

                stmt.executeUpdate();
                System.out.println("Inserted show for movieId: " + movieId);
            } catch (Exception e) {
                System.out.println("ERROR inserting show: " + e.getMessage());
            }
    }

    public List<String> getAllShows() {
        List<String> shows = new ArrayList<>();

        String sql = """
                SELECT s.id, m.name, s.show_time
                FROM show s
                JOIN movies m ON s.movie_id = m.id
                """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    shows.add(
                            rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("show_time")
                    );
                }
            } catch (Exception e) {
            System.out.println("ERROR fetching shows: " + e.getMessage());
        }

        return shows;
    }
}

