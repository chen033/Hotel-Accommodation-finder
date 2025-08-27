package org.example;

public class Room {
    private String type;
    private double price;
    private String location;
    private String facilities;
    private boolean available;

    public Room(String type, double price, String location, String facilities, boolean available) {
        this.type = type;
        this.price = price;
        this.location = location;
        this.facilities = facilities;
        this.available = available;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getLocation() { return location; }
    public String getFacilities() { return facilities; }
    public boolean isAvailable() { return available; }

    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Room{" +
                "type='" + type + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", facilities='" + facilities + '\'' +
                ", available=" + available +
                '}';
    }
}
