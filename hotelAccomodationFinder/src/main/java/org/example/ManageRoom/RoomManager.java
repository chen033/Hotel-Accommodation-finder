package org.example.ManageRoom;

import DatabaseConnection.SQLiteConnection;
import java.sql.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class RoomManager {

    // LinkedList to store rooms dynamically in memory
    private final LinkedList<Room> rooms = new LinkedList<>();
    private final Connection connection = SQLiteConnection.connect();

    public RoomManager() {
        loadRoomsFromDB(); // load rooms from database into LinkedList
    }

    private void loadRoomsFromDB() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Room")) {
            rooms.clear(); // clear LinkedList before loading
            while (rs.next()) {
                // add to LinkedList using addLast()
                rooms.addLast(new Room(
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

    // Search LinkedList for a room
    public Room getRoomByNumber(int roomNumber) {
        ListIterator<Room> it = rooms.listIterator();
        while (it.hasNext()) {
            Room r = it.next();
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }

    // Get next room number for a floor
    public int getNextRoomNumberForFloor(int floorLevel) {
        int max = floorLevel * 100;
        for (Room r : rooms) {
            if (r.getFloorLevel() == floorLevel && r.getRoomNumber() > max) max = r.getRoomNumber();
        }
        return max + 1;
    }

    // ---------- Add using LinkedList algorithm ----------
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO Room (roomNumber,type,roomView,floorLevel,budgetPerNight,facilities,guestNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Add to DB
            pstmt.setInt(1, room.getRoomNumber());
            pstmt.setString(2, room.getType());
            pstmt.setString(3, room.getRoomView());
            pstmt.setInt(4, room.getFloorLevel());
            pstmt.setDouble(5, room.getBudgetPerNight());
            pstmt.setString(6, room.getFacilities());
            pstmt.setInt(7, room.getGuestNumber());
            pstmt.executeUpdate();

            // Add to LinkedList (at end)
            rooms.addLast(room);
            return true;
        } catch (SQLException e) {
            System.out.println("Add room failed: " + e.getMessage());
            return false;
        }
    }

    // ---------- Update using LinkedList algorithm ----------
    // Modified to also update roomView
    public boolean updateRoom(int roomNumber, String type, String roomView, double budget, String facilities, int guests) {
        String sql = "UPDATE Room SET type=?, roomView=?, budgetPerNight=?, facilities=?, guestNumber=? WHERE roomNumber=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Update DB
            pstmt.setString(1, type);
            pstmt.setString(2, roomView);
            pstmt.setDouble(3, budget);
            pstmt.setString(4, facilities);
            pstmt.setInt(5, guests);
            pstmt.setInt(6, roomNumber);
            int updated = pstmt.executeUpdate();

            if (updated > 0) {
                // Update in LinkedList using ListIterator
                ListIterator<Room> it = rooms.listIterator();
                while (it.hasNext()) {
                    Room r = it.next();
                    if (r.getRoomNumber() == roomNumber) {
                        r.setType(type);
                        r.setBudgetPerNight(budget);
                        r.setFacilities(facilities);
                        r.setGuestNumber(guests);
                        // roomView and floorLevel are final in Room class; to update view, recreate Room object in list
                        Room updatedRoom = new Room(r.getRoomNumber(), type, roomView, r.getFloorLevel(), budget, facilities, guests);
                        it.set(updatedRoom);
                        break;
                    }
                }
            }
            return updated > 0;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    // ---------- Delete using LinkedList algorithm ----------
    public boolean deleteRoom(int roomNumber) {
        String sql = "DELETE FROM Room WHERE roomNumber=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Delete from DB
            pstmt.setInt(1, roomNumber);
            pstmt.executeUpdate();

            // Delete from LinkedList using ListIterator.remove()
            ListIterator<Room> it = rooms.listIterator();
            while (it.hasNext()) {
                if (it.next().getRoomNumber() == roomNumber) {
                    it.remove();
                    break;
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }

    // ---------- Display all rooms using LinkedList traversal ----------
    public void displayRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }
        System.out.println("Rooms:");
        ListIterator<Room> it = rooms.listIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public LinkedList<Room> getRooms() {
        return rooms;
    }
}
