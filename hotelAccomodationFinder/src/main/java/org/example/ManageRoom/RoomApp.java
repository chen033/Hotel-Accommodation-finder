package org.example.ManageRoom;

import java.util.Scanner;

public class RoomApp {
    private final RoomManager roomManager = new RoomManager();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n--- Room Manager ---");
            System.out.println("1. Add Room\n2. Display Rooms\n3. Update Room\n4. Delete Room\n5. Exit");
            int choice = getIntInput("Choose an option: ", 1, 5);

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> roomManager.displayRooms();
                case 3 -> updateRoom();
                case 4 -> deleteRoom();
                case 5 -> System.exit(0);
            }
        }
    }

    private void addRoom() {
        int floor = getIntInput("Floor Level (1-5): ", 1, 5);
        int roomNumber = roomManager.getNextRoomNumberForFloor(floor);
        System.out.println("Assigned Room Number: " + roomNumber);

        String type = getOptionInput("Room Type (Single/Double/Suite): ", "Single","Double","Suite");
        String view = getOptionInput("Room View (Sea/Garden/City): ", "Sea","Garden","City");
        double budget = getPositiveDouble("Budget per Night: ");
        String facilities = getFacilities();
        int guests = getIntInput("Guest Number: ", 1, 20);

        Room room = new Room(roomNumber, type, view, floor, budget, facilities, guests);
        System.out.println(roomManager.addRoom(room) ? "Room added!" : "Failed to add room!");
    }

    private void updateRoom() {
        roomManager.displayRooms();
        int roomNumber = getIntInput("Enter Room Number to update: ");
        Room r = roomManager.getRoomByNumber(roomNumber);
        if (r == null) { System.out.println("Room not found!"); return; }

        String type = getOptionInput("New Type (Single/Double/Suite): ", "Single","Double","Suite");
        double budget = getPositiveDouble("New Budget per Night: ");
        String facilities = getFacilities();
        int guests = getIntInput("New Guest Number: ", 1, 20);

        System.out.println(roomManager.updateRoomFields(roomNumber, type, budget, facilities, guests) ? "Updated!" : "Failed!");
    }

    private void deleteRoom() {
        roomManager.displayRooms();
        int roomNumber = getIntInput("Enter Room Number to delete: ");
        System.out.println(roomManager.deleteRoom(roomNumber) ? "Deleted!" : "Failed!");
    }

    // ---------- Input Helpers ----------
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Invalid number!"); }
        }
    }

    private int getIntInput(String prompt, int min, int max) {
        int val;
        do { val = getIntInput(prompt); } while (val < min || val > max);
        return val;
    }

    private double getPositiveDouble(String prompt) {
        double val;
        do {
            System.out.print(prompt);
            try { val = Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { val = -1; }
        } while (val <= 0);
        return val;
    }

    private String getOptionInput(String prompt, String... options) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            for (String o : options) if (o.equalsIgnoreCase(input)) return o;
            System.out.println("Invalid! Choose: " + String.join("/", options));
        }
    }

    private String getFacilities() {
        String[] allowed = {"wifi","aircondition","pool"};
        while (true) {
            System.out.print("Facilities (wifi,aircondition,pool): ");
            String input = sc.nextLine().trim();
            boolean valid = true;
            for (String f : input.split(",")) {
                boolean match = false;
                for (String a : allowed) if (a.equalsIgnoreCase(f.trim())) match = true;
                if (!match) { valid = false; break; }
            }
            if (valid) return input;
            System.out.println("Invalid facilities!");
        }
    }
}
