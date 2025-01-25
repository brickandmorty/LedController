package at.edu.c02.ledcontroller;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ApiServiceImplTest {
    @Test
    public void e2ePutRequest() throws IOException {
        int id = 55;
        String color = "#ff0000";

        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.putLight(id, color, false);
        assertFalse(apiService.getLight(55).getJSONArray("lights").getJSONObject(0).getBoolean("on"));

        apiService.putLight(id, color, true);
        assertTrue(apiService.getLight(55).getJSONArray("lights").getJSONObject(0).getBoolean("on"));

    }
}
