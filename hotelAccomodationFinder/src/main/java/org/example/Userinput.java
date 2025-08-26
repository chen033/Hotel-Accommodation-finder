package org.example;

import org.example.UserPreferences;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Userinput {

    public static Queue<UserPreferences> getUserPreferences() {
        Scanner scanner = new Scanner(System.in);
        Queue<UserPreferences> preferencesQueue = new LinkedList<>();

        System.out.println("=== Welcome to Hotel Finder ===");

        // MinBudget
        int Minbudget;
        while (true) {
            System.out.print("Enter your   minimum  budget : ");
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                Minbudget = Integer.parseInt(input);
                break;
            } else {
                System.out.println(" Invalid input. Please enter a number.");
            }
        }

        // MaxBudget
        int Maxbudget;
        while (true) {
            System.out.print("Enter your   maximum budget : ");
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                Maxbudget = Integer.parseInt(input);
                break;
            } else {
                System.out.println(" Invalid input. Please enter a number.");
            }
        }



        System.out.print("Are you traveling for business or leisure?");
        String travel = scanner.nextLine();

        // Room Type
        String roomType;
        while (true) {
            System.out.print("Room type (Single/Double/Suite): ");
            roomType = scanner.nextLine().trim();
            if (roomType.equalsIgnoreCase("Single") ||
                    roomType.equalsIgnoreCase("Double") ||
                    roomType.equalsIgnoreCase("Suite")) {
                break;
            } else {
                System.out.println(" Invalid choice. Please enter Single, Double, or Suite.");
            }
        }

        String view;
        while (true) {
            System.out.print("Do you prefer a city view, garden view, or sea view?\n ");
            view = scanner.nextLine().trim();
            if (view.equalsIgnoreCase("sea") ||
                    view.equalsIgnoreCase("garden") ||
                    view.equalsIgnoreCase("city")) {
                break;
            } else {
                System.out.println(" Invalid choice. Please enter Sea,Garden or City.");
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

        // Parking
        boolean parking = askYesNo(scanner, "Do you need Parking? (Yes/No): ");

        // Guests
        int guests;
        while (true) {
            System.out.print("Number of guests: ");
            String input = scanner.nextLine();
            if (input.matches("\\d+") && Integer.parseInt(input) > 0) {
                guests = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Invalid input. Please enter a positive number.");
            }
        }

        // Create UserPreferences object and add to queue
        UserPreferences userPreferences = new UserPreferences(
                Minbudget, Maxbudget,travel, roomType,view,floorlvl,wifi, ac, parking, guests
        );
        preferencesQueue.add(userPreferences);

        System.out.println("\n Preferences saved successfully.");
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
