package app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static void main(String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5432/movie_booking";
            String user = "postgres";
            String password = "postgres"; // subject to change

            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to DB successfully!");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
