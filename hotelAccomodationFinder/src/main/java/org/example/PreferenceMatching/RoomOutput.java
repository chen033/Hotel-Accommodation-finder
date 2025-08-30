package org.example.PreferenceMatching;

import java.util.ArrayList;
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

  /*  public static void printBudgetMatchedRooms(List<org.example.ManageRoom.Room> rooms, org.example.UserInput.UserPreferences prefs) {
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
    }*/

    // New: print list of scored rooms (from matchByBudget) up to 10
    public static void printBudgetScoredRooms(List<RoomMatcher.ScoredRoom> scoredRooms, org.example.UserInput.UserPreferences prefs) {
        System.out.println("\n=== Rooms matched by budget (top results) ===");
        if (scoredRooms == null || scoredRooms.isEmpty()) {
            System.out.println("No rooms available in the system.");
            return;
        }

        List<RoomMatcher.ScoredRoom> inside = new ArrayList<>();
        List<RoomMatcher.ScoredRoom> outside = new ArrayList<>();

        double min = prefs.getMinbudget();
        double max = prefs.getMaxbudget();

        for (RoomMatcher.ScoredRoom sr : scoredRooms) {
            double b = sr.room.getBudgetPerNight();
            if (b >= min && b <= max) inside.add(sr);
            else outside.add(sr);
        }

        int printed = 0;

        // Print inside-budget results first
        if (!inside.isEmpty()) {
            System.out.println("\n-- Rooms within your budget --");
            for (RoomMatcher.ScoredRoom sr : inside) {
                if (printed >= 10) break;
                System.out.println((printed + 1) + ". " + sr.room + " | Budget: " + sr.room.getBudgetPerNight() + " | Score: " + sr.score);
                printed++;
            }
        }

        // If fewer than 10, fill with best outside-budget results (scoredRooms is already expected to be sorted by score desc)
        if (printed < 10 && !outside.isEmpty()) {
            System.out.println("\n-- Additional rooms close to your budget --");
            for (RoomMatcher.ScoredRoom sr : outside) {
                if (printed >= 10) break;
                System.out.println((printed + 1) + ". " + sr.room + " | Budget: " + sr.room.getBudgetPerNight() + " | Score: " + sr.score);
                printed++;
            }
        }

        if (printed == 0) {
            System.out.println("No rooms available for the given budget.");
        } else if (printed < 10) {
            System.out.println("\nDisplayed " + printed + " result(s). Fewer than 10 rooms exist that match or are close to your budget.");
        }
    }
}
