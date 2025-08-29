package org.example.PreferenceMatching;

import java.util.List;

public class RoomOutput {
    public static void printRankedRooms(List<RoomMatcher.ScoredRoom> rankedRooms) {
        System.out.println("\n=== Top Ranked Rooms based on your Preferences ===");
        int count = 0;
        for (RoomMatcher.ScoredRoom sr : rankedRooms) {
            if (count >= 10) break;
            System.out.println(sr.room + " | Score: " + sr.score);
            count++;
        }
        if (count == 0) {
            System.out.println("No rooms matched your preferences.");
        }
    }

    public static void printBudgetMatchedRooms(List<org.example.ManageRoom.Room> rooms, org.example.UserInput.UserPreferences prefs) {
        System.out.println("\n=== Rooms Matched by Budget ===");
        int count = 0;
        for (org.example.ManageRoom.Room room : rooms) {
            double budget = room.getBudgetPerNight();
            if (budget >= prefs.getMinbudget() && budget <= prefs.getMaxbudget()) {
                System.out.println(room);
                count++;
                if (count >= 10) break;
            }
        }
        if (count == 0) {
            System.out.println("No rooms matched your budget criteria.");
        }
    }
}
