package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    // Path to your SQLite database file
    private static final String URL = "jdbc:sqlite:C:/Users/User/Downloads/Hotel-Accommodation-finder.db";


    // Method to get a connection
    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Connected to SQLite database!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }
}
