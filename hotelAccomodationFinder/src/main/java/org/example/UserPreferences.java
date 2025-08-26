package org.example;
public class UserPreferences {
    private int Minbudget;
    private int Maxbudget;
    private String travel;
    private String roomType;
    private String view;
    private int floorlvl;
    private boolean wifi;
    private boolean airConditioning;
    private boolean parking;
    private int guests;


    public UserPreferences(int Minbudget,int Maxbudget, String travel, String roomType,String view,int floorlvl,
                           boolean wifi, boolean airConditioning,
                           boolean parking, int guests) {
        this.Minbudget = Minbudget;
        this.Maxbudget = Maxbudget;
        this.travel=travel;
        this.roomType = roomType;
        this.view=view;
        this.floorlvl=floorlvl;
        this.wifi = wifi;
        this.airConditioning = airConditioning;
        this.parking = parking;
        this.guests = guests;
    }

    public int getMinbudgetudget() { return Minbudget; }
    public int getMaxbudget() { return Maxbudget; }
    public String getTravel() { return travel; }
    public String getRoomType() { return roomType; }
    public String getView() { return view; }
    public int getFloorlvl() { return floorlvl; }
    public boolean isWifi() { return wifi; }
    public boolean isAirConditioning() { return airConditioning; }
    public boolean isParking() { return parking; }
    public int getGuests() { return guests; }

    @Override
    public String toString() {
        return "UserPreferences {" +
                "Minbudget=" + Minbudget +
                "Maxbudget=" + Maxbudget +
                ", travel='" + travel+ '\'' +
                ", roomType='" + roomType + '\'' +
                ", view='" + view + '\'' +
                ", FloorLevel='" + floorlvl  +
                ", wifi=" + wifi +
                ", airConditioning=" + airConditioning +
                ", parking=" + parking +
                ", guests=" + guests +
                '}';
    }
}
