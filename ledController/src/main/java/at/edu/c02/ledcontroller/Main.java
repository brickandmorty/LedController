package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) throws IOException {
        String groupName = "";
        LedController ledController = new LedControllerImpl(new ApiServiceImpl());

        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!input.equalsIgnoreCase("exit")) {
            System.out.println("=== LED Controller ===");
            System.out.println("Enter 'demo' to fetch all lights");
            System.out.println("Enter 'group <name>' to get the status of LEDs in a group (e.g., 'group B')");
            System.out.println("Enter 'groupstatus <name<' to get the status of all LEDs in a group");
            System.out.println("Enter 'exit' to exit the program");
            input = reader.readLine();

            if (input.equalsIgnoreCase("demo")) {
                ledController.demo();
            }
            else if(input.startsWith("groupstatus")) {
                if (groupName.equals("")){
                    System.out.println("Please insert group name first!");
                } else {
                    System.out.println("Fetching LED Status for group: " + groupName);
                    JSONArray groupLeds = ledController.getGroupLeds(groupName);

                    for (int i = 0; i < groupLeds.length(); i++) {
                        // Get the current LED object (JSONObject)
                        JSONObject led = groupLeds.getJSONObject(i);

                        // Get the "on" status for this LED
                        boolean isOn = led.getBoolean("on");

                        if (isOn) {
                            System.out.println("LED " + led.getInt("id") + " is currently on: Color: " + led.getString("color"));
                        } else {
                            System.out.println("LED " + led.getInt("id") + " is currently off: " + isOn + " Color: " + led.getString("color"));


                        }

                    }
                }
            }
            else if (input.startsWith("group")) {
                try {
                    groupName = input.split(" ", 2)[1]; // Gruppenname extrahieren
                    System.out.println("Fetching LEDs for group: " + groupName);
                    System.out.println(ledController.getGroupLeds(groupName).toString(2));
                } catch (Exception e) {
                    System.err.println("Invalid input. Please use 'group <name>'.");
                }
            }


        }
    }
}

