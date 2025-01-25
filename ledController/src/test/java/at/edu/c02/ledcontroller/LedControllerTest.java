package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LedControllerTest {
    /**
     * This test is just here to check if tests get executed. Feel free to delete it when adding your own tests.
     * Take a look at the stack calculator tests again if you are unsure where to start.
     */
    @Test
    public void dummyTest() {
        assertEquals(1, 1);
    }

    @Test
    public void mockGroupsTest() throws Exception{
        ApiServiceImpl apiService = mock(ApiServiceImpl.class);

        String jsonResponse = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("api.json").toURI())));

        when(apiService.getLights()).thenReturn(new JSONObject(jsonResponse));

        LedControllerImpl controller = new LedControllerImpl(apiService);

        JSONArray result = controller.getGroupLeds("A");

        assertNotNull(result);

        // Read the expected result from the group_A_result.json file
        String expectedJsonResponse = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("group_A_result.json").toURI())));
        JSONObject expectedResult = new JSONObject(expectedJsonResponse);

        assertEquals(expectedResult.getJSONArray("lights").toString(), result.toString());
    }

}
