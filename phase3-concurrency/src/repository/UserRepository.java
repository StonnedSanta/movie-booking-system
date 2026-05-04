package repository;

import java.sql.*;
import java.util.*;

public class UserRepository {
    
    private final String url = "jdbc:postgresql://localhost:5432/movie_booking";
    private final String user = "postgres";
    private final String password = "postgres";

    public void addUser(String name, String email) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?) ON CONFLICT (email) DO NOTHING";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.executeUpdate();

                System.out.println("Inserted: " + name);

            } catch (Exception e) {
                System.out.println("ERROR inserting user: " + e.getMessage());
            }
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();

        String sql = "SELECT name FROM users";

        try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs =  stmt.executeQuery(sql)) {
        
            while (rs.next()) {
                users.add(rs.getString("name"));
            }
            
        } catch (Exception e) {
            System.out.println("ERROR fetching user: " + e.getMessage());
        } 

        return users;
    }   
}