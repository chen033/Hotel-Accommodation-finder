package org.example.PreferenceMatching;

import java.util.ArrayList;
import java.util.List;

public class RoomOutput {
    public static void printRankedRooms(List<RoomMatcher.ScoredRoom> rankedRooms) {
        System.out.println("\n=== Top Ranked Rooms based on your Preferences ===");
        int count = 0;
        for (RoomMatcher.ScoredRoom sr : rankedRooms) {
            if (count >= 10) break;
            double base = sr.room.getBudgetPerNight();
            double adjusted = sr.adjustedBudget;
            String outputLine;
            if (Double.compare(adjusted, base) == 0) {
                outputLine = sr.room + " | Score: " + sr.score;
            } else {
                String roomStr = sr.room.toString();
                double diff = adjusted - base;
                String businessInfo = String.format(" | Business Budget: LKR.%,.0f (+LKR.%,.0f) | Meals: Breakfast & Dinner free", adjusted, diff);
                outputLine = roomStr + businessInfo + " | Score: " + sr.score;
            }
            System.out.println(outputLine);
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
            double b = sr.adjustedBudget;
            if (b >= min && b <= max) inside.add(sr);
            else outside.add(sr);
        }

        int printed = 0;

        if (!inside.isEmpty()) {
            System.out.println("\n-- Rooms within your budget --");
            for (RoomMatcher.ScoredRoom sr : inside) {
                if (printed >= 10) break;
                double base = sr.room.getBudgetPerNight();
                double adjusted = sr.adjustedBudget;
                String line;
                if (Double.compare(adjusted, base) == 0) {
                    line = (printed + 1) + ". " + sr.room + " | Score: " + sr.score;
                } else {
                    String roomStr = sr.room.toString();
                    double diff = adjusted - base;
                    String businessInfo = String.format(" | Business Budget: LKR.%,.0f (+LKR.%,.0f) | Meals: Breakfast & Dinner free", adjusted, diff);
                    line = (printed + 1) + ". " + roomStr + businessInfo + " | Score: " + sr.score;
                }
                System.out.println(line);
                printed++;
            }
        }

        if (printed < 10 && !outside.isEmpty()) {
            System.out.println("\n-- Additional rooms close to your budget --");
            for (RoomMatcher.ScoredRoom sr : outside) {
                if (printed >= 10) break;
                double base = sr.room.getBudgetPerNight();
                double adjusted = sr.adjustedBudget;
                String line;
                if (Double.compare(adjusted, base) == 0) {
                    line = (printed + 1) + ". " + sr.room + " | Score: " + sr.score;
                } else {
                    String roomStr = sr.room.toString();
                    double diff = adjusted - base;
                    String businessInfo = String.format(" | Business Budget: LKR.%,.0f (+LKR.%,.0f) | Meals: Breakfast & Dinner free", adjusted, diff);
                    line = (printed + 1) + ". " + roomStr + businessInfo + " | Score: " + sr.score;
                }
                System.out.println(line);
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
