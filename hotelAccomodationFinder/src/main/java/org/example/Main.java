package org.example;

import java.util.Queue;

import static org.example.Userinput.getUserPreferences;

public class Main {
    public static void main(String[] args) {

        Queue<UserPreferences> userPrefsQueue = getUserPreferences();

        System.out.println("Your Preferences10");
        for (UserPreferences prefs : userPrefsQueue) {
            System.out.println(prefs);
        }

    }
}
