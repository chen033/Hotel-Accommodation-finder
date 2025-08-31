package org.example.UserInput;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import org.example.ManageRoom.RoomManager;
import org.example.ManageRoom.Room;

public class Userinput {

    public static Queue<UserPreferences> getUserPreferences(Scanner scanner) {
        // Scanner scanner = new Scanner(System.in);
        Queue<UserPreferences> preferencesQueue = new LinkedList<>();

        System.out.println("=== Welcome to Hotel room Finder ===");

        // Determine real-time budget bounds from DB
        RoomManager roomManager = new RoomManager();
        LinkedList<Room> roomList = roomManager.getRooms();
        int dbMinBudget = 5000;
        int dbMaxBudget = 50000;
        if (roomList != null && !roomList.isEmpty()) {
            dbMinBudget = Integer.MAX_VALUE;
            dbMaxBudget = Integer.MIN_VALUE;
            for (Room r : roomList) {
                int b = (int) Math.round(r.getBudgetPerNight());
                if (b < dbMinBudget) dbMinBudget = b;
                if (b > dbMaxBudget) dbMaxBudget = b;
            }
            // Fallback if something went wrong
            if (dbMinBudget == Integer.MAX_VALUE || dbMaxBudget == Integer.MIN_VALUE) {
                dbMinBudget = 5000;
                dbMaxBudget = 50000;
            }
        }

        // Budget inputs (wrap both min and max in an outer loop so we can re-enter min if max < min)
        int Minbudget;
        int Maxbudget;

        outerLoop:
        while (true) {
            // MinBudget
            while (true) {
                System.out.print("Enter your minimum budget (" + dbMinBudget + " - " + dbMaxBudget + "): ");
                String input = scanner.nextLine();
                if (input.matches("\\d+")) {
                    Minbudget = Integer.parseInt(input);
                    if (Minbudget >= dbMinBudget && Minbudget <= dbMaxBudget) {
                        break;
                    } else {
                        System.out.println(" Budget must be between " + dbMinBudget + " and " + dbMaxBudget + ".");
                         // go back to outer loop to re-enter min
                    }
                } else {
                    System.out.println(" Invalid input. Please enter a number.");
                }
            }

            // MaxBudget
            while (true) {
                System.out.print("Enter your maximum budget (" + dbMinBudget + " - " + dbMaxBudget + "): ");
                String input = scanner.nextLine();
                if (input.matches("\\d+")) {
                    Maxbudget = Integer.parseInt(input);
                    if (Maxbudget >= dbMinBudget && Maxbudget <= dbMaxBudget) {
                        if (Maxbudget >= Minbudget) {
                            break; // valid pair, exit max loop and then outer
                        } else {
                            System.out.println(" Maximum budget must be greater than or equal to minimum budget.");
                            // go back to outer loop so Minbudget is requested again
                            continue outerLoop;
                        }
                    } else {
                        System.out.println(" Budget must be between " + dbMinBudget + " and " + dbMaxBudget + ".");
                        // go back to outer loop to re-enter min
                    }
                } else {
                    System.out.println(" Invalid input. Please enter a number.");
                }
            }

            // If we reach here both Minbudget and Maxbudget are valid
            break;
        }

        // Travel purpose
        String travel;
        while (true) {
            System.out.print("Are you traveling for Business or Leisure? ");
            travel = scanner.nextLine().trim();

            if (travel.equalsIgnoreCase("Business") || travel.equalsIgnoreCase("Leisure")) {
                break;
            } else {
                System.out.println(" Invalid choice. Please enter Business or Leisure.");
            }
        }

        // Room Type
        int roomType;
        while (true) {
            System.out.println("Select Room Type:");
            System.out.println("1. Single");
            System.out.println("2. Double");
            System.out.println("3. Suite");
            System.out.print("Enter choice (1/2/3): ");
            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                roomType = Integer.parseInt(input);
                break;
            } else {
                System.out.println(" Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        // View
        int view;
        while (true) {
            System.out.println("Do you prefer a Sea view, Garden view, or City view?");
            System.out.println("1. Sea");
            System.out.println("2. Garden");
            System.out.println("3. City");
            System.out.print("Enter choice (1/2/3): ");
            String input = scanner.nextLine().trim();

            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                view = Integer.parseInt(input);
                break;
            } else {
                System.out.println(" Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        // floorlvl
        int floorlvl;
        while (true) {
            System.out.print("What is your preferred floor level? : ");
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                floorlvl = Integer.parseInt(input);
                break;
            } else {
                System.out.println(" Invalid input. Please enter a number.");
            }
        }

        // WiFi
        boolean wifi = askYesNo(scanner, "Do you need WiFi? (Yes/No): ");

        // Air Conditioning
        boolean ac = askYesNo(scanner, "Do you need Air Conditioning? (Yes/No): ");

        // Pool
        boolean pool = askYesNo(scanner, "Do you need pool access? (Yes/No): ");

        // Guests (validate based on room type)
        int guests;
        while (true) {
            System.out.print("Number of guests: ");
            String input = scanner.nextLine();

            if (input.matches("\\d+") && Integer.parseInt(input) > 0) {
                guests = Integer.parseInt(input);

                boolean valid = false;
                switch (roomType) {
                    case 1: // Single
                        if (guests == 1) valid = true;
                        break;
                    case 2: // Double
                        if (guests <= 2) valid = true;
                        break;
                    case 3: // Suite
                        if (guests <= 4) valid = true;
                        break;
                }

                if (valid) {
                    break;
                } else {
                    System.out.println(" Invalid number of guests for the selected room type.");
                    switch (roomType) {
                        case 1:
                            System.out.println(" Single room can only have 1 guest.");
                            break;
                        case 2:
                            System.out.println(" Double room can have up to 2 guests.");
                            break;
                        case 3:
                            System.out.println(" Suite can have up to 4 guests.");
                            break;
                        default:
                            break;
                    }
                }

            } else {
                System.out.println("Invalid input. Please enter a positive number.");
            }
        }

        // Create UserPreferences object and add to queue
        UserPreferences userPreferences = new UserPreferences(
                Minbudget, Maxbudget, travel, roomType, view, floorlvl, wifi, ac, pool, guests
        );
        preferencesQueue.add(userPreferences);

        System.out.println(" Preferences saved successfully.");

        // Print the user's entered preferences in a single horizontal summary line
        String wifiStr = userPreferences.isWifi() ? "Yes" : "No";
        String acStr = userPreferences.isAirConditioning() ? "Yes" : "No";
        String poolStr = userPreferences.ispool() ? "Yes" : "No";

        String summary = String.format("=== My Preferences === \n Budget: %d-%d  | Travel: %s  | Room: %s  | View: %s  | Floor: %d  | WiFi: %s  | AC: %s  | Pool: %s  | Guests: %d",
                userPreferences.getMinbudget(), userPreferences.getMaxbudget(), userPreferences.getTravel(),
                userPreferences.getRoomTypeName(), userPreferences.getView(), userPreferences.getFloorlvl(),
                wifiStr, acStr, poolStr, userPreferences.getGuests());

        System.out.println(summary);

        // If user is traveling for business, previously we displayed an adjustment block. Removed UI per request.
        // Business UI removed; business surcharges are still applied internally when matching.

        return preferencesQueue;
    }

    private static boolean askYesNo(Scanner scanner, String question) {
        String answer;
        while (true) {
            System.out.print(question);
            answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("Yes")) {
                return true;
            } else if (answer.equalsIgnoreCase("No")) {
                return false;
            } else {
                System.out.println(" Please answer Yes or No.");
            }
        }
    }
}
