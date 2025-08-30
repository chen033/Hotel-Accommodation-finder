package org.example;

import DatabaseConnection.SQLiteConnection;
import org.example.ManageRoom.RoomApp;
import org.example.ManageRoom.RoomManager;
import org.example.PreferenceMatching.RoomMatcher;
import org.example.UserInput.UserPreferences;
import org.example.UserInput.Userinput;

import java.util.List;
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
                // Automatically ask user preferences when selecting this
                Queue<UserPreferences> userPrefsQueue = Userinput.getUserPreferences();

                RoomManager roomManager = new RoomManager(); // Load rooms from DB
                RoomMatcher matcher = new RoomMatcher();

                // Keep original queue usage (matchPreferences will poll it)
                List<RoomMatcher.ScoredRoom> rankedRooms = matcher.matchPreferences(userPrefsQueue, roomManager);

                // Use RoomOutput to display top-ranked rooms (up to 10)
                org.example.PreferenceMatching.RoomOutput.printRankedRooms(rankedRooms);

                // Since matchPreferences consumed the queue, create a fresh queue from user input again for budget-only display
                Queue<UserPreferences> budgetPrefs = Userinput.getUserPreferences();
                if (!budgetPrefs.isEmpty()) {
                    UserPreferences firstPref = budgetPrefs.poll();
                    List<RoomMatcher.ScoredRoom> budgetMatches = matcher.matchByBudget(firstPref, roomManager);
                    org.example.PreferenceMatching.RoomOutput.printBudgetScoredRooms(budgetMatches, firstPref);
                }
            }
            default -> System.out.println("Invalid choice!");
        }
    }
}
