package org.example.UserInput;

public class UserPreferences {
    private int Minbudget;
    private int Maxbudget;
    private String travel;
    private int roomType; // store as number (1,2,3)
    private int view;     // store as number (1,2,3)
    private int floorlvl;
    private boolean wifi;
    private boolean airConditioning;
    private boolean pool;
    private int guests;

    public UserPreferences(int Minbudget, int Maxbudget, String travel, int roomType, int view, int floorlvl,
                           boolean wifi, boolean airConditioning,
                           boolean pool, int guests) {
        this.Minbudget = Minbudget;
        this.Maxbudget = Maxbudget;
        this.travel = travel;
        this.roomType = roomType;
        this.view = view;
        this.floorlvl = floorlvl;
        this.wifi = wifi;
        this.airConditioning = airConditioning;
        this.pool = pool;
        this.guests = guests;
    }

    public int getMinbudget() { return Minbudget; }
    public int getMaxbudget() { return Maxbudget; }
    public String getTravel() { return travel; }

    public int getRoomTypeNumber() { return roomType; }
    public String getRoomTypeName() {
        switch (roomType) {
            case 1: return "Single";
            case 2: return "Double";
            case 3: return "Suite";
            default: return "Unknown";
        }
    }

    public int getViewNumber() { return view; }
    public String getView() {   // keep name consistent with RoomMatcher
        switch (view) {
            case 1: return "Sea";
            case 2: return "Garden";
            case 3: return "City";
            default: return "Unknown";
        }
    }

    public int getFloorlvl() { return floorlvl; }
    public boolean isWifi() { return wifi; }
    public boolean isAirConditioning() { return airConditioning; }
    public boolean ispool() { return pool; }
    public int getGuests() { return guests; }

    @Override
    public String toString() {
        return "UserPreferences {" +
                "Minbudget=" + Minbudget +
                ", Maxbudget=" + Maxbudget +
                ", travel='" + travel + '\'' +
                ", roomType=" + getRoomTypeName() + " (" + roomType + ")" +
                ", view=" + getView() + " (" + view + ")" +
                ", FloorLevel=" + floorlvl +
                ", wifi=" + wifi +
                ", airConditioning=" + airConditioning +
                ", pool=" + pool +
                ", guests=" + guests +
                '}';
    }


}
