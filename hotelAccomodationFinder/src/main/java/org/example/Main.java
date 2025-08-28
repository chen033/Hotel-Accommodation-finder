package org.example;

import org.example.UserInput.UserPreferences;
import org.example.ManageRoom.RoomApp;

import java.util.Queue;
import java.util.Scanner;

import static org.example.UserInput.Userinput.getUserPreferences;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select functionality to run:");
        System.out.println("1. User Preferences");
        System.out.println("2. Room Manager");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        switch(choice) {
            case 1 -> {
                Queue<UserPreferences> userPrefsQueue = getUserPreferences();
                System.out.println("Your Preferences:");
                for (UserPreferences prefs : userPrefsQueue) {
                    System.out.println(prefs);
                }
            }
            case 2 -> {
                RoomApp app = new RoomApp();
                app.start();
            }
            default -> System.out.println("Invalid choice!");
        }
    }
}
