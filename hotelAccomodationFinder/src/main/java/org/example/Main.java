package org.example;

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
        Scanner sc = new Scanner(System.in);
        System.out.println("Select functionality to run:");
        System.out.println("1. Room Manager");
        System.out.println("2. Accomodation finder");
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

                List<RoomMatcher.ScoredRoom> rankedRooms = matcher.matchPreferences(userPrefsQueue, roomManager);

                System.out.println("\n=== Ranked Rooms based on your Preferences ===");
                for (RoomMatcher.ScoredRoom sr : rankedRooms) {
                    System.out.println(sr.room + " | Score: " + sr.score);
                }
            }
            default -> System.out.println("Invalid choice!");
        }
    }
}
