package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomManager roomManager = new RoomManager();

        while (true) {
            System.out.println("\n--- Room Manager ---");
            System.out.println("1. Add Room");
            System.out.println("2. Display Rooms");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Room Type: ");
                    String type = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Location: ");
                    String location = sc.nextLine();
                    System.out.print("Facilities: ");
                    String facilities = sc.nextLine();

                    roomManager.addRoom(new Room(type, price, location, facilities, true));
                    System.out.println("Room added successfully!");
                    break;

                case 2:
                    roomManager.displayRooms();
                    break;

                case 3:
                    roomManager.displayRooms();
                    System.out.print("Enter index of room to update: ");
                    int updateIndex = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Room Type: ");
                    String newType = sc.nextLine();
                    System.out.print("New Price: ");
                    double newPrice = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("New Location: ");
                    String newLocation = sc.nextLine();
                    System.out.print("New Facilities: ");
                    String newFacilities = sc.nextLine();

                    roomManager.updateRoom(updateIndex, new Room(newType, newPrice, newLocation, newFacilities, true));
                    System.out.println("Room updated successfully!");
                    break;

                case 4:
                    roomManager.displayRooms();
                    System.out.print("Enter index of room to delete: ");
                    int deleteIndex = sc.nextInt();
                    sc.nextLine();
                    roomManager.deleteRoom(deleteIndex);
                    System.out.println("Room deleted successfully!");
                    break;

                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
