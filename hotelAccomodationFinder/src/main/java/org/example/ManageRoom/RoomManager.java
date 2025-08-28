package org.example.ManageRoom;

import DatabaseConnection.SQLiteConnection;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RoomManager {
    private final List<Room> rooms = new LinkedList<>();
    private final Connection connection = SQLiteConnection.connect();

    public RoomManager() {
        loadRoomsFromDB();
    }

    private void loadRoomsFromDB() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Room")) {
            rooms.clear();
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("roomNumber"),
                        rs.getString("type"),
                        rs.getString("roomView"),
                        rs.getInt("floorLevel"),
                        rs.getDouble("budgetPerNight"),
                        rs.getString("facilities"),
                        rs.getInt("guestNumber")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Failed to load rooms: " + e.getMessage());
        }
    }

    public Room getRoomByNumber(int roomNumber) {
        return rooms.stream().filter(r -> r.getRoomNumber() == roomNumber).findFirst().orElse(null);
    }

    public int getNextRoomNumberForFloor(int floorLevel) {
        int max = floorLevel * 100;
        for (Room r : rooms)
            if (r.getFloorLevel() == floorLevel && r.getRoomNumber() > max)
                max = r.getRoomNumber();
        return max + 1;
    }

    public boolean addRoom(Room room) {
        String sql = "INSERT INTO Room (roomNumber,type,roomView,floorLevel,budgetPerNight,facilities,guestNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, room.getRoomNumber());
            pstmt.setString(2, room.getType());
            pstmt.setString(3, room.getRoomView());
            pstmt.setInt(4, room.getFloorLevel());
            pstmt.setDouble(5, room.getBudgetPerNight());
            pstmt.setString(6, room.getFacilities());
            pstmt.setInt(7, room.getGuestNumber());
            pstmt.executeUpdate();
            rooms.add(room);
            return true;
        } catch (SQLException e) {
            System.out.println("Add room failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRoomFields(int roomNumber, String type, double budget, String facilities, int guests) {
        String sql = "UPDATE Room SET type=?, budgetPerNight=?, facilities=?, guestNumber=? WHERE roomNumber=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setDouble(2, budget);
            pstmt.setString(3, facilities);
            pstmt.setInt(4, guests);
            pstmt.setInt(5, roomNumber);
            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                Room r = getRoomByNumber(roomNumber);
                r.setType(type);
                r.setBudgetPerNight(budget);
                r.setFacilities(facilities);
                r.setGuestNumber(guests);
            }
            return updated > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRoom(int roomNumber) {
        String sql = "DELETE FROM Room WHERE roomNumber=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            pstmt.executeUpdate();
            rooms.removeIf(r -> r.getRoomNumber() == roomNumber);
            return true;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }

    public void displayRooms() {
        if (rooms.isEmpty()) { System.out.println("No rooms available."); return; }
        System.out.println("Rooms:");
        for (Room r : rooms) System.out.println(r);
    }

    public List<Room> getRooms() { return rooms; }
}
