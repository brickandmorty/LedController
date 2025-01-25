package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ApiServiceImplTest {


    @Test
    public void e2ePutRequest() throws IOException {
        int id = 55;
        String color = "#ff0000";

        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.putLight(id, color, false);
        assertFalse(apiService.getLights(55).getJSONArray("lights").getJSONObject(0).getBoolean("on"));

        apiService.putLight(id, color, true);
        assertTrue(apiService.getLights(55).getJSONArray("lights").getJSONObject(0).getBoolean("on"));
    }



    @Test
    public void testTurnOffAllLeds() throws IOException {
        ApiService apiServiceMock = Mockito.mock(ApiService.class);
        JSONObject lightsData = new JSONObject().put("lights", new JSONArray()
                .put(new JSONObject().put("id", 1).put("color", "#ff0000").put("on", true))
                .put(new JSONObject().put("id", 2).put("color", "#00ff00").put("on", true)));

        when(apiServiceMock.getLights()).thenReturn(lightsData);
        doNothing().when(apiServiceMock).putLight(anyInt(), anyString(), eq(false));

        LedControllerImpl ledController = new LedControllerImpl(apiServiceMock);
        ledController.turnOffAllLeds();

        verify(apiServiceMock, times(1)).getLights();
        verify(apiServiceMock, times(2)).putLight(anyInt(), anyString(), eq(false));
    }

    private ApiService verify(ApiService apiServiceMock, VerificationMode times) {
        return apiServiceMock;
    }
}

