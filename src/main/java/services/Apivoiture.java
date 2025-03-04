package services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Apivoiture {

    private static final String API_KEY = "87ng05ftz_5cb8yjuze_5voyvpose"; // Replace with your API key
    private static final String BASE_URL = "https://api.carsxe.com/";

    public static List<String> getMakes() {
        String urlString = BASE_URL + "makes?key=" + API_KEY;
        return fetchListFromAPI(urlString);
    }

    public static List<String> getModels(String make) {
        String urlString = BASE_URL + "models?key=" + API_KEY + "&make=" + URLEncoder.encode(make, StandardCharsets.UTF_8);
        return fetchListFromAPI(urlString);
    }

    public static String getCarSpecs(String vin) {
        String urlString = BASE_URL + "specs?key=" + API_KEY + "&vin=" + URLEncoder.encode(vin, StandardCharsets.UTF_8);
        return fetchDataFromAPI(urlString);
    }

    private static List<String> fetchListFromAPI(String urlString) {
        try {
            System.out.println("Fetching data from: " + urlString); // Debug: Log the URL

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode); // Debug: Log the response code

            if (responseCode != 200) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                errorReader.close();
                System.err.println("API Error: " + errorResponse.toString()); // Debug: Log the error response
                return Collections.singletonList("Erreur: Impossible de récupérer les données.");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            System.out.println("API Response: " + response.toString()); // Debug: Log the API response

            // Convert JSON response to List
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray dataArray = jsonResponse.getJSONArray("data");

            List<String> result = new ArrayList<>();
            for (int i = 0; i < dataArray.length(); i++) {
                result.add(dataArray.getString(i));
            }
            return result;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage()); // Debug: Log the exception
            return Collections.singletonList("Erreur: " + e.getMessage());
        }
    }

    private static String fetchDataFromAPI(String urlString) {
        try {
            System.out.println("Fetching data from: " + urlString); // Debug: Log the URL

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode); // Debug: Log the response code

            if (responseCode != 200) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                errorReader.close();
                System.err.println("API Error: " + errorResponse.toString()); // Debug: Log the error response
                return "Erreur: Impossible de récupérer les données.";
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            System.out.println("API Response: " + response.toString()); // Debug: Log the API response
            return response.toString();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage()); // Debug: Log the exception
            return "Erreur: " + e.getMessage();
        }
    }
}