package org.example;

import DatabaseConnection.SQLiteConnection;
import org.example.ManageRoom.RoomApp;
import org.example.ManageRoom.RoomManager;
import org.example.PreferenceMatching.RoomMatcher;
import org.example.UserInput.UserPreferences;
import org.example.UserInput.Userinput;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Connection is no longer stored here; SQLiteConnection.connect() is used inside managers where needed.

        // RoomSeeder removed: database seeding is handled externally.

        // 3️⃣ Now start your app logic
        Scanner sc = new Scanner(System.in);
        System.out.println("Select functionality to run:");
        System.out.println("1. Room Manager");
        System.out.println("2. Accommodation finder");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline


        switch (choice) {
            case 1 -> {
                RoomApp app = new RoomApp();
                app.start();
            }
            case 2 -> {
                // Collect user preferences once and create copies so we don't re-prompt the user
                Queue<UserPreferences> userPrefsQueue = Userinput.getUserPreferences();

                // Make two independent copies; matchPreferences will poll its copy
                Queue<UserPreferences> prefsForMatching = new LinkedList<>(userPrefsQueue);
                Queue<UserPreferences> prefsForBudget = new LinkedList<>(userPrefsQueue);

                RoomManager roomManager = new RoomManager(); // Load rooms from DB
                RoomMatcher matcher = new RoomMatcher();

                // Use a copy for matching (preserves original userPrefsQueue elsewhere if needed)
                List<RoomMatcher.ScoredRoom> rankedRooms = matcher.matchPreferences(prefsForMatching, roomManager);

                // Use RoomOutput to display top-ranked rooms (up to 10)
                org.example.PreferenceMatching.RoomOutput.printRankedRooms(rankedRooms);

                // For budget-only matching, use the first preference from prefsForBudget (no re-prompt)
                if (!prefsForBudget.isEmpty()) {
                    UserPreferences firstPref = prefsForBudget.poll();
                    List<RoomMatcher.ScoredRoom> budgetMatches = matcher.matchByBudget(firstPref, roomManager);
                    org.example.PreferenceMatching.RoomOutput.printBudgetScoredRooms(budgetMatches, firstPref);
                } else {
                    System.out.println("No preference provided for budget-only matching.");
                }
            }
            default -> System.out.println("Invalid choice!");
        }
    }
}
