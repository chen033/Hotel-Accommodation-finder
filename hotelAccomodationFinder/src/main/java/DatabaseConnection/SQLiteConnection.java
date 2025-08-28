package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:C:\\Users\\User\\Downloads\\Hotel-Room-finder.db";

    public static Connection connect() {
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
