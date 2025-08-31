package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class SQLiteConnection {

    private static final String URL = "jdbc:sqlite:C:\\Hotel-Accommodation-finder\\hotelAccomodationFinder\\Hotel-Room-finder.db";

    public static Connection connect() {
        File dbFile = new File("C:\\Hotel-Accommodation-finder\\hotelAccomodationFinder\\Hotel-Room-finder.db");
        if (!dbFile.exists()) {
            System.out.println("Database file not found at: " + dbFile.getAbsolutePath());
            return null;
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver class not found on classpath: " + e.getMessage());
        }

        try {
            Connection conn = DriverManager.getConnection(URL);
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
