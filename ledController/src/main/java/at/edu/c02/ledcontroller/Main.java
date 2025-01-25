package at.edu.c02.ledcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        LedController ledController = new LedControllerImpl(new ApiServiceImpl());

        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!input.equalsIgnoreCase("exit")) {
            System.out.println("=== LED Controller ===");
            System.out.println("Enter 'demo' to fetch all lights");
            System.out.println("Enter 'group <name>' to get the status of LEDs in a group (e.g., 'group B')");
            System.out.println("Enter 'exit' to exit the program");
            input = reader.readLine();

            if (input.equalsIgnoreCase("demo")) {
                ledController.demo();
            } else if (input.startsWith("group")) {
                try {
                    String groupName = input.split(" ", 2)[1]; // Gruppenname extrahieren
                    System.out.println("Fetching LEDs for group: " + groupName);
                    System.out.println(ledController.getGroupLeds(groupName).toString(2));
                } catch (Exception e) {
                    System.err.println("Invalid input. Please use 'group <name>'.");
                }
            }
        }
    }

