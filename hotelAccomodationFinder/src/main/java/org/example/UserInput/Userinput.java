package org.example.UserInput;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Userinput {

    public static Queue<UserPreferences> getUserPreferences() {
        Scanner scanner = new Scanner(System.in);
        Queue<UserPreferences> preferencesQueue = new LinkedList<>();

        System.out.println("=== Welcome to Hotel room Finder ===");

        // MinBudget
        int Minbudget;
        while (true) {
            System.out.print("Enter your minimum budget (5000 - 50000): ");
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                Minbudget = Integer.parseInt(input);
                if (Minbudget >= 5000 && Minbudget <= 50000) {
                    break;
                } else {
                    System.out.println(" Budget must be between 5000 and 50000.");
                }
            } else {
                System.out.println(" Invalid input. Please enter a number.");
            }
        }


        // MaxBudget
        int Maxbudget;
        while (true) {
            System.out.print("Enter your maximum budget (5000 - 50000): ");
            String input = scanner.nextLine();
            if (input.matches("\\d+")) {
                Maxbudget = Integer.parseInt(input);
                if (Maxbudget >= 5000 && Maxbudget <= 50000) {
                    if (Maxbudget >= Minbudget) {
                        break;
                    } else {
                        System.out.println(" Maximum budget must be greater than or equal to minimum budget.");
                    }
                } else {
                    System.out.println(" Budget must be between 5000 and 50000.");
                }
            } else {
                System.out.println(" Invalid input. Please enter a number.");
            }
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

        String view;
        while (true) {
            System.out.print("Do you prefer a city view, garden view, or sea view?\n ");
            view = scanner.nextLine().trim();
            if (view.equalsIgnoreCase("sea") ||
                    view.equalsIgnoreCase("garden") ||
                    view.equalsIgnoreCase("" +
                            "")) {
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

        // Pool
        boolean pool = askYesNo(scanner, "Do you need the pool access  (Yes/No): ");

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
                        if (guests <= 4) valid = true; // you can adjust max
                        break;
                }

                if (valid) {
                    break;
                } else {
                    System.out.println(" Invalid number of guests for the selected room type.");
                    if (roomType == 1) System.out.println(" Single room can only have 1 guest.");
                    else if (roomType == 2) System.out.println(" Double room can have up to 2 guests.");
                    else if (roomType == 3) System.out.println(" Suite can have up to 4 guests.");
                }

            } else {
                System.out.println("Invalid input. Please enter a positive number.");
            }
        }


        // Create UserPreferences object and add to queue
        UserPreferences userPreferences = new UserPreferences(
                Minbudget, Maxbudget,travel,roomType,view,floorlvl,wifi, ac,pool, guests
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
