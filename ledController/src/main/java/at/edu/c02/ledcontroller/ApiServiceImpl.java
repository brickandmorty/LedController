package at.edu.c02.ledcontroller;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class handles all HTTP communication with the server.
 */
public class ApiServiceImpl implements ApiService {

    private static final String BASE_URL = "https://balanced-civet-91.hasura.app/api/rest/";
    private static final String GROUP_ID_HEADER = "X-Hasura-Group-ID";
    private static final String GROUP_SECRET = "5f26cca3877ad";
    // Secret in Textdatei rauslöschen aus der Klasse


    @Override
    public JSONObject getLights() throws IOException {
        URL url = new URL(BASE_URL + "getLights");
        return getJSONObject(url);
    }

    @Override
    public JSONObject getLight(int id) throws IOException {
        URL url = new URL(BASE_URL + "lights/" + id);
        return getJSONObject(url);
    }

    @Override
    public JSONObject putLight(int id, String color, Boolean state) throws IOException {
        URL url = new URL(BASE_URL + "setLight");
        return sendPutRequest(url, createJsonPayload(id, color, state));
    }

    /**
     * Helper method to handle GET requests and parse the response into a JSONObject.
     *
     * @param url the URL for the GET request
     * @return JSONObject containing the response
     * @throws IOException if the request fails
     */
    private JSONObject getJSONObject(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty(GROUP_ID_HEADER, GROUP_SECRET);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error: GET request failed with response code " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
            return new JSONObject(response.toString());
        }
    }

    /**
     * Helper method to send a PUT request with a JSON payload and parse the response into a JSONObject.
     *
     * @param url       the URL for the PUT request
     * @param jsonInput the JSON payload as a string
     * @return JSONObject containing the response
     * @throws IOException if the request fails
     */
    private JSONObject sendPutRequest(URL url, String jsonInput) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty(GROUP_ID_HEADER, GROUP_SECRET);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line.trim());
                }
                throw new IOException("Error: PUT request failed with response code " + responseCode + " - " + errorResponse);
            }
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
            return new JSONObject(response.toString());
        }
    }

    /**
     * Helper method to create the JSON payload for the PUT request.
     *
     * @param id    the ID of the light
     * @param color the color of the light
     * @param state the state of the light (on/off)
     * @return JSON payload as a string
     */
    private String createJsonPayload(int id, String color, Boolean state) {
        return String.format("{\"id\": %d, \"color\": \"%s\", \"state\": %b}", id, color, state);
    }
}