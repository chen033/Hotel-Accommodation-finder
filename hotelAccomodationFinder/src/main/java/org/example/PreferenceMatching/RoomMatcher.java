package org.example.PreferenceMatching;

import org.example.ManageRoom.Room;
import org.example.ManageRoom.RoomManager;
import org.example.UserInput.UserPreferences;

import java.util.*;

public class RoomMatcher {

    public static class ScoredRoom {
        public Room room;
        public double score;
        public double adjustedBudget;

        public ScoredRoom(Room room, double score, double adjustedBudget) {
            this.room = room;
            this.score = score;
            this.adjustedBudget = adjustedBudget;
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
                double adjustedBudget = applyBusinessSurchargeIfNeeded(room, user);
                double score = calculateScore(user, room, adjustedBudget);
                ScoredRoom sr = new ScoredRoom(room, score, adjustedBudget);

                if (score >= 70) {
                    rankedRooms.add(sr);
                    perfectMatchFound = true;
                    break;
                } else if (score > 0) {
                    partialStack.push(sr);
                }
            }


            if (!perfectMatchFound && !partialStack.isEmpty()) {
                ScoredRoom bestPartial = findBestPartial(partialStack);
                rankedRooms.add(bestPartial);
            }
            partialStack.clear();
        }

        rankedRooms.sort((a, b) -> Double.compare(b.score, a.score));
        return rankedRooms;
    }

    public List<ScoredRoom> matchByBudget(UserPreferences pref, RoomManager roomManager) {
        List<ScoredRoom> scored = new ArrayList<>();
        LinkedList<Room> availableRooms = roomManager.getRooms();

        double min = pref.getMinbudget();
        double max = pref.getMaxbudget();

        for (Room room : availableRooms) {
            double adjustedBudget = applyBusinessSurchargeIfNeeded(room, pref);
            double diff;
            if (adjustedBudget >= min && adjustedBudget <= max) {
                diff = 0;
            } else if (adjustedBudget < min) {
                diff = min - adjustedBudget;
            } else {
                diff = adjustedBudget - max;
            }
            double score = Math.max(0, 100 - Math.min(100, diff));
            scored.add(new ScoredRoom(room, score, adjustedBudget));
        }

        scored.sort((a, b) -> {
            int c = Double.compare(b.score, a.score);
            if (c != 0) return c;
            return Double.compare(a.adjustedBudget, b.adjustedBudget);
        });

        return scored;
    }

    private double calculateScore(UserPreferences user, Room room, double adjustedBudget) {
        double score = 0;

        if (adjustedBudget >= user.getMinbudget() && adjustedBudget <= user.getMaxbudget())
            score += 30;

        if (room.getType().equalsIgnoreCase(user.getRoomTypeName()))
            score += 20;

        if (room.getRoomView().equalsIgnoreCase(user.getView()))
            score += 10;

        if (room.getFloorLevel() == user.getFloorlvl())
            score += 10;

        String fac = room.getFacilities().toLowerCase();
        if (user.isWifi() && fac.contains("wifi")) score += 7;
        if (user.isAirConditioning() && fac.contains("aircondition")) score += 7;
        if (user.ispool() && fac.contains("pool")) score += 6;

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

    private double applyBusinessSurchargeIfNeeded(Room room, UserPreferences user) {
        double base = room.getBudgetPerNight();
        if (user == null || user.getTravel() == null) return base;
        if (!user.getTravel().equalsIgnoreCase("Business")) return base;

        String type = room.getType();
        if (type == null) return base;
        if (type.equalsIgnoreCase("Single")) return base + 3000;
        if (type.equalsIgnoreCase("Double")) return base + 5000;
        if (type.equalsIgnoreCase("Suite")) return base + 10000;
        // default no surcharge for unknown types
        return base;
    }
}
