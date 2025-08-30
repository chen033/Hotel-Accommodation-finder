package org.example.ManageRoom;

import java.util.Scanner;

public class RoomApp {
    private final RoomManager roomManager = new RoomManager();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n--- Room Manager ---");
            System.out.println("1. Add Room\n2. Display Rooms\n3. Update Room\n4. Delete Room\n5. Back to Main Menu");
            int choice = getIntInput("Choose an option: ", 1, 5);

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> roomManager.displayRooms();
                case 3 -> updateRoom();
                case 4 -> deleteRoom();
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return; // go back to main menu instead of exiting the JVM
                }
            }
        }
    }

    // ---------- Add Room ----------
    private void addRoom() {
        int floor = getIntInput("Floor Level (1-5): ", 1, 5);
        int roomNumber = roomManager.getNextRoomNumberForFloor(floor);
        System.out.println("Assigned Room Number: " + roomNumber);

        String type = getRoomType();
        String view = getRoomView();
        double budget = getBudget();
        String facilities = getFacilities();
        int guests = getIntInput("Guest Number: ", 1, 20);

        Room room = new Room(roomNumber, type, view, floor, budget, facilities, guests);

        if (roomManager.addRoom(room)) {
            System.out.println("✅ Room added!");
        } else {
            System.out.println("❌ Failed to add room!");
        }
    }

    // ---------- Update Room ----------
    private void updateRoom() {
        roomManager.displayRooms();

        int roomNumber = getIntInput("Enter Room Number to update: ");
        Room r = roomManager.getRoomByNumber(roomNumber);
        if (r == null) {
            System.out.println("❌ Room not found!");
            return;
        }

        String type = getRoomType();
        String view = getRoomView();
        double budget = getBudget();
        String facilities = getFacilities();
        int guests = getIntInput("New Guest Number: ", 1, 20);

        // pass 'view' to updateRoom to match new signature
        if (roomManager.updateRoom(roomNumber, type, view, budget, facilities, guests)) {
            System.out.println("✅ Room updated!");
        } else {
            System.out.println("❌ Failed to update room!");
        }
    }

    // ---------- Delete Room ----------
    private void deleteRoom() {
        roomManager.displayRooms();
        int roomNumber = getIntInput("Enter Room Number to delete: ");

        if (roomManager.deleteRoom(roomNumber)) {
            System.out.println("✅ Room deleted!");
        } else {
            System.out.println("❌ Failed to delete room!");
        }
    }

    // ---------- Input Helpers ----------
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number!");
            }
        }
    }

    private int getIntInput(String prompt, int min, int max) {
        int val;
        do {
            val = getIntInput(prompt);
            if (val < min || val > max) {
                System.out.println("❌ Value must be between " + min + " and " + max + "!");
            }
        } while (val < min || val > max);
        return val;
    }

    private double getBudget() {
        double val;
        do {
            System.out.print("Budget per Night (5000 - 50000): ");
            try {
                val = Double.parseDouble(sc.nextLine().trim());
                if (val < 5000 || val > 50000) {
                    System.out.println("❌ Invalid budget! Must be between 5000 and 50000.");
                    val = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Enter a valid number.");
                val = -1;
            }
        } while (val <= 0);
        return val;
    }

    private String getRoomType() {
        System.out.println("Select Room Type:");
        System.out.println("1. Single");
        System.out.println("2. Double");
        System.out.println("3. Suite");
        int choice = getIntInput("Enter choice (1-3): ", 1, 3);
        return switch (choice) {
            case 1 -> "Single";
            case 2 -> "Double";
            case 3 -> "Suite";
            default -> "Single";
        };
    }

    private String getRoomView() {
        System.out.println("Select Room View:");
        System.out.println("1. Sea");
        System.out.println("2. Garden");
        System.out.println("3. City");
        int choice = getIntInput("Enter choice (1-3): ", 1, 3);
        return switch (choice) {
            case 1 -> "Sea";
            case 2 -> "Garden";
            case 3 -> "City";
            default -> "Sea";
        };
    }

    private String getFacilities() {
        String[] options = {"wifi", "aircondition", "pool"};
        System.out.println("Select Facilities (enter numbers separated by commas):");
        System.out.println("1. wifi");
        System.out.println("2. aircondition");
        System.out.println("3. pool");

        while (true) {
            System.out.print("Enter choices (e.g., 1,2 or 1,2,3): ");
            String input = sc.nextLine().trim();
            String[] parts = input.split(",");
            StringBuilder sb = new StringBuilder();
            boolean valid = true;

            for (String p : parts) {
                try {
                    int num = Integer.parseInt(p.trim());
                    if (num < 1 || num > 3) {
                        valid = false;
                        break;
                    }
                    if (sb.length() != 0) sb.append(",");
                    sb.append(options[num - 1]);
                } catch (NumberFormatException e) {
                    valid = false;
                    break;
                }
            }

            if (valid) return sb.toString();
            System.out.println("❌ Invalid facilities! Please enter numbers between 1 and 3 separated by commas.");
        }
    }
}
