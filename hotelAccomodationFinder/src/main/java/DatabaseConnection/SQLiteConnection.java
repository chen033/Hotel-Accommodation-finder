package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class SQLiteConnection {

    private static final String URL = "jdbc:sqlite:C:\\Users\\Senesh\\IdeaProjects\\Hotel-Accommodation-finder\\hotel.db";

    public static Connection connect() {
        // Check if the database file exists
        File dbFile = new File("C:\\Users\\Senesh\\IdeaProjects\\Hotel-Accommodation-finder\\hotel.db");
        if (!dbFile.exists()) {
            System.out.println("Database file not found at: " + dbFile.getAbsolutePath());
            return null;
        }

        // Try to explicitly load the SQLite JDBC driver to give a clearer error if it's not on the classpath
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver class not found on classpath: " + e.getMessage());
            // continue; DriverManager may still work if driver auto-registers, but warn user.
        }

        try {
            Connection conn = DriverManager.getConnection(URL);
           // System.out.println("Connected to SQLite database!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
