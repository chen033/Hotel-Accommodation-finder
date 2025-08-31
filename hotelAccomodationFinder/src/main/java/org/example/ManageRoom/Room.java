package org.example.ManageRoom;

public class Room {
    private final int roomNumber;
    private String type;
    private final String roomView;
    private final int floorLevel;
    private double budgetPerNight;
    private String facilities;
    private int guestNumber;

    public Room(int roomNumber, String type, String roomView, int floorLevel,
                double budgetPerNight, String facilities, int guestNumber) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.roomView = roomView;
        this.floorLevel = floorLevel;
        this.budgetPerNight = budgetPerNight;
        this.facilities = facilities;
        this.guestNumber = guestNumber;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public String getRoomView() { return roomView; }
    public int getFloorLevel() { return floorLevel; }
    public double getBudgetPerNight() { return budgetPerNight; }
    public String getFacilities() { return facilities; }
    public int getGuestNumber() { return guestNumber; }

    public void setType(String type) { this.type = type; }
    public void setBudgetPerNight(double budgetPerNight) { this.budgetPerNight = budgetPerNight; }
    public void setFacilities(String facilities) { this.facilities = facilities; }
    public void setGuestNumber(int guestNumber) { this.guestNumber = guestNumber; }

    @Override
    public String toString() {
        return roomNumber + " | " + type + " | " + roomView + " | Floor: " + floorLevel +
                " | Budget: LKR." + budgetPerNight + " | Facilities: " + facilities +
                " | Guests: " + guestNumber;
    }
}
