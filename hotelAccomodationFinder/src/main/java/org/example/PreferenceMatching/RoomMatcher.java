package org.example.PreferenceMatching;

import org.example.ManageRoom.Room;
import org.example.ManageRoom.RoomManager;
import org.example.UserInput.UserPreferences;

import java.util.*;

public class RoomMatcher {

    public static class ScoredRoom {
        public Room room;
        public double score;

        public ScoredRoom(Room room, double score) {
            this.room = room;
            this.score = score;
        }
    }

    public List<ScoredRoom> matchPreferences(Queue<UserPreferences> userPrefs, RoomManager roomManager) {
        List<ScoredRoom> rankedRooms = new ArrayList<>();
        Stack<ScoredRoom> partialStack = new Stack<>();
        LinkedList<Room> availableRooms = roomManager.getRooms();

        while (!userPrefs.isEmpty()) {
            UserPreferences user = userPrefs.poll();
            boolean perfectMatchFound = false;

            for (Room room : availableRooms) {
                double score = calculateScore(user, room);
                ScoredRoom sr = new ScoredRoom(room, score);

                if (score >= 70) { // Perfect match threshold
                    rankedRooms.add(sr);
                    perfectMatchFound = true;
                    break; // stop at first perfect match
                } else if (score > 0) {
                    partialStack.push(sr); // store partial match
                }
            }

            // If no perfect match, backtrack to best partial match
            if (!perfectMatchFound && !partialStack.isEmpty()) {
                ScoredRoom bestPartial = findBestPartial(partialStack);
                rankedRooms.add(bestPartial);
            }

            partialStack.clear(); // clear stack for next preference
        }

        // Sort rooms by score descending
        rankedRooms.sort((a, b) -> Double.compare(b.score, a.score));
        return rankedRooms;
    }

    // New: match only by budget, return up to 10 best-scored rooms
    public List<ScoredRoom> matchByBudget(UserPreferences pref, RoomManager roomManager) {
        List<ScoredRoom> scored = new ArrayList<>();
        LinkedList<Room> availableRooms = roomManager.getRooms();

        double min = pref.getMinbudget();
        double max = pref.getMaxbudget();

        for (Room room : availableRooms) {
            double budget = room.getBudgetPerNight();
            double diff;
            if (budget >= min && budget <= max) {
                diff = 0; // inside budget is best
            } else if (budget < min) {
                diff = min - budget;
            } else {
                diff = budget - max;
            }
            // Score: higher is better. Inside budget gives highest score (100).
            // For outside budget, penalize by difference but cap penalty so scores remain comparable.
            double score = Math.max(0, 100 - Math.min(100, diff));
            scored.add(new ScoredRoom(room, score));
        }

        // Sort by score desc, then by budget asc for tie-breaker
        scored.sort((a, b) -> {
            int c = Double.compare(b.score, a.score);
            if (c != 0) return c;
            return Double.compare(a.room.getBudgetPerNight(), b.room.getBudgetPerNight());
        });

        // Ensure at least up to 10 results (return all if fewer)
        if (scored.size() > 10) {
            return new ArrayList<>(scored.subList(0, 10));
        }
        return scored;
    }

    private double calculateScore(UserPreferences user, Room room) {
        double score = 0;

        // Budget
        if (room.getBudgetPerNight() >= user.getMinbudget() && room.getBudgetPerNight() <= user.getMaxbudget())
            score += 30;

        // Room type
        if (room.getType().equalsIgnoreCase(user.getRoomTypeName()))
            score += 20;

        // View
        if (room.getRoomView().equalsIgnoreCase(user.getView()))
            score += 10;

        // Floor
        if (room.getFloorLevel() == user.getFloorlvl())
            score += 10;

        // Facilities
        String fac = room.getFacilities().toLowerCase();
        if (user.isWifi() && fac.contains("wifi")) score += 7;
        if (user.isAirConditioning() && fac.contains("aircondition")) score += 7;
        if (user.ispool() && fac.contains("pool")) score += 6;

        // Guests
        if (room.getGuestNumber() >= user.getGuests()) score += 10;

        return score;
    }

    private ScoredRoom findBestPartial(Stack<ScoredRoom> stack) {
        ScoredRoom best = stack.pop();
        while (!stack.isEmpty()) {
            ScoredRoom current = stack.pop();
            if (current.score > best.score) best = current;
        }
        return best;
    }
}
