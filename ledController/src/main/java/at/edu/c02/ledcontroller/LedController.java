package at.edu.c02.ledcontroller;

import java.io.IOException;
import org.json.JSONArray;

public interface LedController {
    void demo() throws IOException;

    JSONArray getGroupLeds(String groupName) throws IOException;

    // Schaltet alle LEDs aus
    void turnOffAllLeds() throws IOException;
}
