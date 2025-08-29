package DatabaseConnection;

import org.example.ManageRoom.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RoomSeeder {

    public static void seedRooms(Connection connection) {
        List<Room> rooms = Arrays.asList(
                // Floor 1
                new Room(101, "Single", "Sea", 1, 5000, "wifi", 1),
                new Room(102, "Single", "City", 1, 7000, "wifi,aircondition", 1),
                new Room(103, "Single", "Garden", 1, 9000, "wifi,pool", 1),
                new Room(104, "Double", "Sea", 1, 12000, "wifi,aircondition", 2),
                new Room(105, "Double", "City", 1, 15000, "wifi,pool", 2),
                new Room(106, "Double", "Garden", 1, 18000, "wifi,aircondition,pool", 2),
                new Room(107, "Suite", "Sea", 1, 20000, "wifi,aircondition,pool", 4),
                new Room(108, "Suite", "City", 1, 25000, "wifi,aircondition", 3),
                new Room(109, "Suite", "Garden", 1, 30000, "wifi", 2),
                new Room(110, "Suite", "Sea", 1, 35000, "wifi,aircondition,pool", 4),

                // Floor 2
                new Room(201, "Single", "Sea", 2, 6000, "wifi", 1),
                new Room(202, "Single", "City", 2, 8000, "wifi,aircondition", 1),
                new Room(203, "Single", "Garden", 2, 10000, "wifi,pool", 1),
                new Room(204, "Double", "Sea", 2, 14000, "wifi,aircondition", 2),
                new Room(205, "Double", "City", 2, 16000, "wifi,pool", 2),
                new Room(206, "Double", "Garden", 2, 19000, "wifi,aircondition,pool", 2),
                new Room(207, "Suite", "Sea", 2, 22000, "wifi,aircondition,pool", 4),
                new Room(208, "Suite", "City", 2, 27000, "wifi,aircondition", 3),
                new Room(209, "Suite", "Garden", 2, 32000, "wifi", 2),
                new Room(210, "Suite", "Sea", 2, 36000, "wifi,aircondition,pool", 4),

                // Floor 3
                new Room(301, "Single", "Sea", 3, 6500, "wifi", 1),
                new Room(302, "Single", "City", 3, 8500, "wifi,aircondition", 1),
                new Room(303, "Single", "Garden", 3, 11000, "wifi,pool", 1),
                new Room(304, "Double", "Sea", 3, 14500, "wifi,aircondition", 2),
                new Room(305, "Double", "City", 3, 17000, "wifi,pool", 2),
                new Room(306, "Double", "Garden", 3, 20000, "wifi,aircondition,pool", 2),
                new Room(307, "Suite", "Sea", 3, 23000, "wifi,aircondition,pool", 4),
                new Room(308, "Suite", "City", 3, 28000, "wifi,aircondition", 3),
                new Room(309, "Suite", "Garden", 3, 34000, "wifi", 2),
                new Room(310, "Suite", "Sea", 3, 39000, "wifi,aircondition,pool", 4),

                // Floor 4
                new Room(401, "Single", "Sea", 4, 7000, "wifi", 1),
                new Room(402, "Single", "City", 4, 9500, "wifi,aircondition", 1),
                new Room(403, "Single", "Garden", 4, 12000, "wifi,pool", 1),
                new Room(404, "Double", "Sea", 4, 15000, "wifi,aircondition", 2),
                new Room(405, "Double", "City", 4, 18000, "wifi,pool", 2),
                new Room(406, "Double", "Garden", 4, 21000, "wifi,aircondition,pool", 2),
                new Room(407, "Suite", "Sea", 4, 26000, "wifi,aircondition,pool", 4),
                new Room(408, "Suite", "City", 4, 31000, "wifi,aircondition", 3),
                new Room(409, "Suite", "Garden", 4, 37000, "wifi", 2),
                new Room(410, "Suite", "Sea", 4, 42000, "wifi,aircondition,pool", 4),

                // Floor 5
                new Room(501, "Single", "Sea", 5, 7500, "wifi", 1),
                new Room(502, "Single", "City", 5, 10000, "wifi,aircondition", 1),
                new Room(503, "Single", "Garden", 5, 12500, "wifi,pool", 1),
                new Room(504, "Double", "Sea", 5, 15500, "wifi,aircondition", 2),
                new Room(505, "Double", "City", 5, 18500, "wifi,pool", 2),
                new Room(506, "Double", "Garden", 5, 22000, "wifi,aircondition,pool", 2),
                new Room(507, "Suite", "Sea", 5, 27000, "wifi,aircondition,pool", 4),
                new Room(508, "Suite", "City", 5, 33000, "wifi,aircondition", 3),
                new Room(509, "Suite", "Garden", 5, 40000, "wifi", 2),
                new Room(510, "Suite", "Sea", 5, 48000, "wifi,aircondition,pool", 4)
        );

        String sql = "INSERT OR IGNORE INTO Room (roomNumber, type, roomView, floorLevel, budgetPerNight, facilities, guestNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Room room : rooms) {
                pstmt.setInt(1, room.getRoomNumber());
                pstmt.setString(2, room.getType());
                pstmt.setString(3, room.getRoomView());
                pstmt.setInt(4, room.getFloorLevel());
                pstmt.setDouble(5, room.getBudgetPerNight());
                pstmt.setString(6, room.getFacilities());
                pstmt.setInt(7, room.getGuestNumber());
                pstmt.executeUpdate();
            }
            System.out.println("✅ 50 Room seed data inserted successfully.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to insert seed data: " + e.getMessage());
        }
    }

}
