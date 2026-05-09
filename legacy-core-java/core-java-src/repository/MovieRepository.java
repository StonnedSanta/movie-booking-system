package repository;

import java.util.*;
import model.Movie;
import java.sql.*;

public class MovieRepository {
    
    private final String url = "jdbc:postgresql://localhost:5432/movie_booking";
    private final String user = "postgres";
    private final String password = "postgres";

    public void addMovie(Movie movie) {
        
        String sql = """
        INSERT INTO movies (name, genre, rating) 
        VALUES (?, ?, ?)
        ON CONFLICT (name) DO NOTHING
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, movie.getName());
                stmt.setString(2, movie.getGenre());
                stmt.setDouble(3, movie.getRating());

                stmt.executeUpdate();
                System.out.println("Inserted movie: " + movie.getName());
            } catch (Exception e) {
                System.out.println("ERROR inserting movie: " + e.getMessage());
            }
    }

    public List<Movie> getAllMovies() {
        
        List<Movie> movies = new ArrayList<>();
        
        String sql = "SELECT * FROM movies";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("genre"),
                        rs.getDouble("rating")
                    ));                   
                }
            } catch (Exception e) {
                System.out.println("ERROR fetching movies: " + e.getMessage());
            }

            return movies;
    }

}
