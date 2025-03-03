package controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.EquipementAffichage;
import models.User;
import models.Offre;
//import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import services.OffreService;
import services.UserService;
import utils.MyDb;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import static controllers.loginuserController.loggedInUser;

public class profileController implements Initializable {


    @FXML
    private AnchorPane Acceuil_form;

    @FXML
    private Button VoitureBtn;

    @FXML
    private ImageView acceuil;

    @FXML
    private Button acceuilBtn;

    @FXML
    private ImageView admin;

    @FXML
    private TextField adresseTextfield1;

    @FXML
    private ImageView ajout;

    @FXML
    private Button avisBtn;

    @FXML
    private AnchorPane avis_form;

    @FXML
    private TextField cinTextfield1;

    @FXML
    private ImageView clients;


    @FXML
    private Button close;

    @FXML
    private Button full;

    @FXML
    private Button deleteBtn2;

    @FXML
    private ImageView déconnexion;

    @FXML
    private TextField emailTextfield1;

    @FXML
    private Button equipBtn;

    @FXML
    private AnchorPane equip_form;

    @FXML
    private Label errormessage;

    @FXML
    private TextField idTextfield1;

    @FXML
    private ImageView logo;


    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField nomTextfield1;

    @FXML
    private TextField prenomTextfield1;

    @FXML
    private Button profileBtn;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private Button readBtn2;

    @FXML
    private Button recBtn;

    @FXML
    private AnchorPane rec_form;

    @FXML
    private Button servicesBtn;

    @FXML
    private AnchorPane services_form;

    @FXML
    private PasswordField setPasswordfield1;

    @FXML
    private TextField telTextfield1;

    @FXML
    private Label usernaame;

    @FXML
    private TextField usernameTextfield1;

    @FXML
    private AnchorPane voiture_form;

    @FXML
    private ImageView photoprofile;

    @FXML
    private GridPane grid;

    @FXML
    private AnchorPane offre_form;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ImageView offre;

    @FXML
    private Button OffreBtn;

    @FXML
    private Label tauxLabel;

    @FXML
    private ImageView equipImageview;


    @FXML
    private Label typeLabel;

    @FXML
    private Label equipeLabel;

    @FXML
    private Label descripLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView offreAutocollant;

    @FXML
    private ImageView offreLabel;

    @FXML
    private File selectedFile;

    @FXML
    private ImageView services;

    @FXML
    private ImageView voitures;

    @FXML
    private ImageView equipements;

    @FXML
    private ImageView avis;

    @FXML
    private ImageView reclamations;
    @FXML
    private AnchorPane pan_form;
    @FXML
    private Button pan;
    @FXML
    private ImageView seaarch;

    @FXML
    private ImageView windspeedImage;

    @FXML
    private Label windspeedText;

    @FXML
    private Label weatherConditionDesc;

    @FXML
    private Label temperatureText;

    @FXML
    private ImageView humidityImage;

    @FXML
    private Label humidityText;


    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField1;

    @FXML
    private ImageView cloudImage;

    @FXML
    private Label surchargeLabel;

    public Button getPan(){
        return pan;
    }
    @FXML
    private ListEquipementClient listc;
    private addreclamation_controller addrec;

    private static final String API_KEY = "8a685ec0b8e559334763210be84f1d32"; // Your OpenWeatherMap API Key
    private static final String CITY = "Tunis";

    private ObservableList<Offre> cardListoffre = FXCollections.observableArrayList();

    private double prevX, prevY, prevWidth, prevHeight;
    private boolean isMaximized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File logoFile = new File("images/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logo.setImage(logoImage);

        File ajoutFile = new File("images/ajout.png");
        Image ajoutImage = new Image(ajoutFile.toURI().toString());
        ajout.setImage(ajoutImage);


        File adminFile = new File("images/admin.png");
        Image adminImage = new Image(adminFile.toURI().toString());
        admin.setImage(adminImage);

        File acceuilFile = new File("images/acceuil.png");
        Image acceuilImage = new Image(acceuilFile.toURI().toString());
        acceuil.setImage(acceuilImage);

        File logoutFile = new File("images/déconnexion.png");
        Image logoutImage = new Image(logoutFile.toURI().toString());
        déconnexion.setImage(logoutImage);

        File photoprofileFile = new File("images/photoprofile.png");
        Image photoprofileImage = new Image(photoprofileFile.toURI().toString());
        photoprofile.setImage(photoprofileImage);

        File offreFile = new File("images/offre.png");
        Image offreImage = new Image(offreFile.toURI().toString());
        offre.setImage(offreImage);

        File offrelabFile = new File("images/offreLabel.png");
        Image offrelabImage = new Image(offrelabFile.toURI().toString());
        offreLabel.setImage(offrelabImage);

        File offreautoFile = new File("images/offreAutocollant.png");
        Image offreautoImage = new Image(offreautoFile.toURI().toString());
        offreAutocollant.setImage(offreautoImage);

        File servicesFile = new File("images/services.png");
        Image servicesImage = new Image(servicesFile.toURI().toString());
        services.setImage(servicesImage);

        File voituresFile = new File("images/voitures.png");
        Image voituresImage = new Image(voituresFile.toURI().toString());
        voitures.setImage(voituresImage);

        File reclamationsFile = new File("images/réclamations.png");
        Image reclamationsImage = new Image(reclamationsFile.toURI().toString());
        reclamations.setImage(reclamationsImage);

        File equipFile = new File("images/equipements.png");
        Image equipImage = new Image(equipFile.toURI().toString());
        equipements.setImage(equipImage);

        File avisFile = new File("images/avis.png");
        Image avisImage = new Image(avisFile.toURI().toString());
        avis.setImage(avisImage);

        File seaFile = new File("images/search1.png");
        Image seaImage = new Image(seaFile.toURI().toString());
        seaarch.setImage(seaImage);

        File cFile = new File("images/cloudy.png");
        Image cImage = new Image(cFile.toURI().toString());
        cloudImage.setImage(cImage);

        File wFile = new File("images/windspeed.png");
        Image wImage = new Image(wFile.toURI().toString());
        windspeedImage.setImage(wImage);

        File hFile = new File("images/humidity.png");
        Image hImage = new Image(hFile.toURI().toString());
        humidityImage.setImage(hImage);


        loadCurrentWeather();
        showLoggedInUserDetails();
        menuDisplayCard();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipementClient.fxml"));
            Parent listcl = loader.load();
            listc = loader.getController();
            System.out.println("ListEquipementClient controller loaded: " + (listc != null ? "not null" : "null"));
            if (listc != null) {
                listc.setDashboardController(this);
                pan_form.getChildren().add(listcl);
                System.out.println("DashboardController passed to ListEquipementClient: not null");
            } else {
                System.err.println("ListEquipementClient controller is null! Check ListEquipementClient.fxml and its controller.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading ListEquipementClient.fxml: " + e.getMessage());
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addreclamation.fxml"));
            Parent addreclamation = loader.load();
            addrec= loader.getController();
            rec_form.getChildren().add(addreclamation);
            addrec.setDashboardController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static JSONObject getWeatherData(String locationName){
        // get location coordinates using the geolocation API
        JSONArray locationData = getLocationData(locationName);

        // extract latitude and longitude data
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        // build API request URL with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Europe%2FBerlin";
        try{
            // call api and get response
            HttpURLConnection conn = fetchApiResponse(urlString);

            // check for response status
            // 200 - means that the connection was a success
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            // store resulting json data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while(scanner.hasNext()){
                // read and store into the string builder
                resultJson.append(scanner.nextLine());
            }

            // close scanner
            scanner.close();

            // close url connection
            conn.disconnect();

            // parse through our data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            // retrieve hourly data
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");

            // we want to get the current hour's data
            // so we need to get the index of our current hour
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            // get temperature
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            // get weather code
            JSONArray weathercode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            // get humidity
            JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            // get windspeed
            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            // build the weather json data object that we are going to access in our frontend
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // retrieves geographic coordinates for given location name
    public static JSONArray getLocationData(String locationName){
        // replace any whitespace in location name to + to adhere to API's request format
        locationName = locationName.replaceAll(" ", "+");

        // build API url with location parameter
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName + "&count=10&language=en&format=json";

        try{
            // call api and get a response
            HttpURLConnection conn = fetchApiResponse(urlString);

            // check response status
            // 200 means successful connection
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }else{
                // store the API results
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                // read and store the resulting json data into our string builder
                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());
                }

                // close scanner
                scanner.close();

                // close url connection
                conn.disconnect();

                // parse the JSON string into a JSON obj
                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                // get the list of location data the API gtenerated from the lcoation name
                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // couldn't find location
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            // attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod("GET");

            // connect to our API
            conn.connect();
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        // could not make connection
        return null;
    }

    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();

        // iterate through the time list and see which one matches our current time
        for(int i = 0; i < timeList.size(); i++){
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                // return the index
                return i;
            }
        }

        return 0;
    }

    public static String getCurrentTime(){
        // get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // format date to be 2023-09-02T00:00 (this is how is is read in the API)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        // format and print the current date and time
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    // convert the weather code to something more readable
    private static String convertWeatherCode(long weathercode){
        String weatherCondition = "";
        if(weathercode == 0L){
            // clear
            weatherCondition = "Clear";
        }else if(weathercode > 0L && weathercode <= 3L){
            // cloudy
            weatherCondition = "Cloudy";
        }else if((weathercode >= 51L && weathercode <= 67L)
                || (weathercode >= 80L && weathercode <= 99L)){
            // rain
            weatherCondition = "Rain";
        }else if(weathercode >= 71L && weathercode <= 77L){
            // snow
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }
    public void handleSearch() {
        // Get location from user
        String userInput = searchTextField1.getText().trim();

        // Validate input - ensure non-empty text
        if (userInput.isEmpty()) {
            return;
        }

        // Retrieve weather data (Assuming WeatherApp.getWeatherData(userInput) returns a Map)
        Map<String, Object> weatherData = getWeatherData(userInput);

        // Update GUI components
        String weatherCondition = (String) weatherData.get("weather_condition");

        if (weatherCondition.equalsIgnoreCase("Rain") || weatherCondition.equalsIgnoreCase("Snow")) {
            surchargeLabel.setWrapText(true);
            surchargeLabel.setText("Une majoration de 20 % sera appliquée en raison des conditions météorologiques défavorables.");
        } else {
            surchargeLabel.setText(""); // No surcharge message
        }

        // Update weather image
        String imagePath = switch (weatherCondition) {
            case "Clear" -> "images/clear.png";
            case "Cloudy" -> "images/cloudy.png";
            case "Rain" -> "images/rain.png";
            case "Snow" -> "images/snow.png";
            default -> null;
        };

        if (imagePath != null) {
            cloudImage.setImage(loadImage(imagePath));
        }

        // Update temperature text
        double temperature = (double) weatherData.get("temperature");
        temperatureText.setText(temperature + " °C");

        // Update weather condition text
        weatherConditionDesc.setText(weatherCondition);

        // Update humidity text
        long humidity = (long) weatherData.get("humidity");
        humidityText.setText("Humidity: " + humidity + "%");

        // Update wind speed text
        double windspeed = (double) weatherData.get("windspeed");
        windspeedText.setText("Windspeed: " + windspeed + " km/h");
    }

    // Load image from file path
    private Image loadImage(String resourcePath) {
        File file = new File(resourcePath);
        if (file.exists()) {
            return new Image(file.toURI().toString());
        } else {
            System.out.println("Could not find resource: " + resourcePath);
            return null;
        }
    }

    public void loadCurrentWeather() {
        String defaultLocation = "Tunis";

        searchTextField1.setText(defaultLocation);

        Map<String, Object> weatherData = getWeatherData(defaultLocation);

        if (weatherData == null) {
            System.out.println("Failed to retrieve weather data.");
            return;
        }


        String weatherCondition = (String) weatherData.get("weather_condition");

        if (weatherCondition.equalsIgnoreCase("Rain") || weatherCondition.equalsIgnoreCase("Snow")) {
            surchargeLabel.setWrapText(true);
            surchargeLabel.setText("Une majoration de 20 % sera appliquée en raison des conditions météorologiques défavorables.");
        } else {
            surchargeLabel.setText("");
        }

        String imagePath = switch (weatherCondition) {
            case "Clear" -> "images/clear.png";
            case "Cloudy" -> "images/cloudy.png";
            case "Rain" -> "images/rain.png";
            case "Snow" -> "images/snow.png";
            default -> null;
        };

        if (imagePath != null) {
            cloudImage.setImage(loadImage(imagePath));
        }

        double temperature = (double) weatherData.get("temperature");
        temperatureText.setText(temperature + " °C");

        weatherConditionDesc.setText(weatherCondition);

        long humidity = (long) weatherData.get("humidity");
        humidityText.setText("Humidity: " + humidity + "%");

        double windspeed = (double) weatherData.get("windspeed");
        windspeedText.setText("Windspeed: " + windspeed + " km/h");
    }
    public void loadPaniersForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Paniers.fxml"));
            Parent paniers = loader.load();
            Paniers paniersController = loader.getController();
            paniersController.setDashboardController(this);
            pan_form.getChildren().clear();
            pan_form.getChildren().add(paniers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadListEquipementClientForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipementClient.fxml"));
            Parent listcl = loader.load();
            listc = loader.getController();
            listc.setDashboardController(this);
            pan_form.getChildren().clear();
            pan_form.getChildren().add(listcl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDetailsForm(EquipementAffichage eq) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Detail_equipement.fxml"));
            Parent detailView = loader.load();
            DetailEquipement detailController = loader.getController();
            detailController.setDashboardController(this);
            detailController.initData(eq);

            pan_form.getChildren().clear();
            pan_form.getChildren().add(detailView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadComForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/historiqueCommande.fxml"));
            Parent coms = loader.load();
            HistoriqueCommande comsController = loader.getController();
            comsController.setDashboardController(this);
            pan_form.getChildren().clear();
            pan_form.getChildren().add(coms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void displayUsername() {
        usernaame.setText(loggedInUser);
    }

    public void toggleMaximize(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds(); // Gets screen size without taskbar

        if (isMaximized) {
            // Restore previous size and position
            stage.setX(prevX);
            stage.setY(prevY);
            stage.setWidth(prevWidth);
            stage.setHeight(prevHeight);
        } else {
            // Save current size and position before maximizing
            prevX = stage.getX();
            prevY = stage.getY();
            prevWidth = stage.getWidth();
            prevHeight = stage.getHeight();

            // Resize to fit the screen
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
        }

        isMaximized = !isMaximized;
    }



    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            try {
                // Load the login interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUser.fxml"));
                Parent root = loader.load();

                // Get current stage (window) and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == acceuilBtn) {
            Acceuil_form.setVisible(true);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            //equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);
            pan_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            //equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            pan.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == profileBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(true);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            //equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);
            pan_form.setVisible(false);

            profileBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            //equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            pan.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == servicesBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(true);
            voiture_form.setVisible(false);
            //equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);
            pan_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            //equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            pan.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == VoitureBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(true);
            //equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);
            pan_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
           // equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            pan.setStyle("-fx-background-color: transparent");




        } else if (event.getSource() == pan) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);

            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);
            pan_form.setVisible(true);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            pan.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");




        } else if (event.getSource() == avisBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            //equip_form.setVisible(false);
            avis_form.setVisible(true);
            rec_form.setVisible(false);
            offre_form.setVisible(false);
            pan_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
           // equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            pan.setStyle("-fx-background-color: transparent");



        } else if (event.getSource() == recBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            //equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(true);
            offre_form.setVisible(false);
            pan_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            //equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            OffreBtn.setStyle("-fx-background-color: transparent");
            pan.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == OffreBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            //equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(true);
            pan_form.setVisible(true);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            //equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            pan.setStyle("-fx-background-color: transparent");



        }

    }


    private boolean isValidInputUpdate() throws Exception {
        StringBuilder errors = new StringBuilder();
        String cin = cinTextfield1.getText();
        String email = emailTextfield1.getText();
        String username = usernameTextfield1.getText();
        String password = setPasswordfield1.getText();
        String tel = telTextfield1.getText();
        String adresse = adresseTextfield1.getText();


        // Validate CIN (7 digits)
        if (!cin.matches("\\d{7}")) {
            errors.append("Erreur : Le CIN doit être composé de 7 chiffres.\n");
        }


        // Validate phone number (8 digits)
        if (!tel.matches("\\d{8}")) {
            errors.append("Erreur : Le numéro de téléphone doit être composé de 8 chiffres.\n");
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.append("Erreur : L'email n'est pas valide.\n");
        }
        if (cin.isBlank() || email.isBlank() || username.isBlank() || password.isBlank() || tel.isBlank() || adresse.isBlank()) {
            errors.append("Veuillez remplir tous les champs. \n");
        }

        // If there are any errors, display them all at once
        if (errors.length() > 0) {
            errormessage.setText(errors.toString()); // Display all accumulated errors
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage.setText(""));
            pause.play();
            return false;  // Validation failed
        }

        return true;  // Validation passed
    }


    public void handleDeleteActionclient(ActionEvent Event) throws Exception {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer votre compte ?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                String idText = idTextfield1.getText();

                int userId = Integer.parseInt(idText);  // Convert the ID from text to integer

                // Create a new User object and set the ID
                User userToDelete = new User();
                userToDelete.setId(userId);  // Set the ID of the user to be deleted

                // Create a new UserService instance for deleting the user
                UserService userService = new UserService();


                userService.delete(userToDelete);

                idTextfield1.clear();
                cinTextfield1.clear();
                nomTextfield1.clear();
                prenomTextfield1.clear();
                telTextfield1.clear();
                emailTextfield1.clear();
                adresseTextfield1.clear();
                usernameTextfield1.clear();
                setPasswordfield1.clear();


                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/LoginUser.fxml"));

                Stage stage = (Stage) ((Node) Event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            errormessage.setText("Erreur : " + e.getMessage());
        }
    }

    public void handleUpdateAction(ActionEvent Event) {
        try {
            // Establish database connection
            MyDb connectNow = new MyDb();
            Connection connectDB = connectNow.getConn();

            // If input validation fails, stop execution
            if (!isValidInputUpdate()) {
                return;
            }

            // Create a new UserService instance
            UserService userService = new UserService();
            User updatedUser = new User();

            updatedUser.setId(Integer.parseInt(idTextfield1.getText()));
            updatedUser.setCin(Integer.parseInt(cinTextfield1.getText()));
            updatedUser.setNom(nomTextfield1.getText());
            updatedUser.setPrenom(prenomTextfield1.getText());
            updatedUser.setTel(Integer.parseInt(telTextfield1.getText()));
            updatedUser.setEmail(emailTextfield1.getText());
            updatedUser.setAdresse(adresseTextfield1.getText());
            updatedUser.setUsername(usernameTextfield1.getText());
            updatedUser.setPassword(setPasswordfield1.getText());
            updatedUser.setRole("client");

            // Retrieve the current image path from the database
            String existingPhotoProfile = userService.getPhotoProfileById(updatedUser.getId());

            // Check if a new image is selected
            if (selectedFile != null) {
                // Use the new image path
                updatedUser.setPhotoProfile(selectedFile.getAbsolutePath());
            } else {
                // Keep the existing image path
                updatedUser.setPhotoProfile(existingPhotoProfile);
            }

            // Call the update method to save changes
            usernaame.setText(updatedUser.getUsername());
            userService.update(updatedUser);

            // Display success message
            errormessage.setText("Utilisateur mis à jour avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage.setText(""));
            pause.play();

        } catch (Exception e) {
            // Handle exceptions, including validation failures or database issues
            errormessage.setText("Erreur : " + e.getMessage());
        }
    }


    @FXML
    private void handleImageSelection() {
        FileChooser fileChooser = new FileChooser();

        // Correctly format the extension filter
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            photoprofile.setImage(image);
        }
    }


    public void showLoggedInUserDetails() {
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        String query = "SELECT * FROM user WHERE username = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, loggedInUser); // Use the stored logged-in username

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idTextfield1.setText(String.valueOf(resultSet.getInt("id")));
                cinTextfield1.setText(String.valueOf(resultSet.getInt("cin")));
                nomTextfield1.setText(resultSet.getString("nom"));
                prenomTextfield1.setText(resultSet.getString("prenom"));
                telTextfield1.setText(String.valueOf(resultSet.getInt("tel")));
                emailTextfield1.setText(resultSet.getString("email"));
                adresseTextfield1.setText(resultSet.getString("adresse"));
                usernameTextfield1.setText(resultSet.getString("username"));
                setPasswordfield1.setText(resultSet.getString("password"));

                // Retrieve the photo path
                String photoProfilePath = resultSet.getString("photo_profile");

                // If photoProfilePath is not null, load and display the image
                if (photoProfilePath != null && !photoProfilePath.trim().isEmpty()) {
                    File file = new File(photoProfilePath);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        photoprofile.setImage(image);
                    } else {
                        System.out.println("Image file not found: " + photoProfilePath);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Offre> getCardListoffre() {
        String sql = "SELECT o.*, e.image FROM offre o " +
                "JOIN equipement e ON o.id_equipement = e.id";

        ObservableList<Offre> listData = FXCollections.observableArrayList();
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String imageUrl = resultSet.getString("image");

                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = "file:/path/to/placeholder.png";  // Ensure a fallback placeholder image
                }

                Offre offre = new Offre(
                        resultSet.getString("type_offre"),
                        resultSet.getString("Description"),
                        resultSet.getDouble("taux_reduction"),
                        resultSet.getString("date_debut"),
                        resultSet.getString("date_fin"),
                        resultSet.getInt("id_equipement"),
                        imageUrl // Use the always-retrieved image from equipement
                );

                listData.add(offre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listData;
    }



    public void menuDisplayCard() {
        cardListoffre.clear();
        cardListoffre.addAll(getCardListoffre());

        int row = 0;
        int column = 0;

        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();

        for (int q = 0; q < cardListoffre.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/Offre.fxml"));
                AnchorPane pane = load.load();

                offreController cardO = load.getController();
                cardO.setData(cardListoffre.get(q)); // Ensure images are set correctly

                // Get the button from the card (assuming it's called 'detailsButton' in your FXML file)
                Button detailsButton = (Button) pane.lookup("#checkBtn"); // Adjust this selector to match your actual button id in FXML

                // Attach event handler to the button only
                if (detailsButton != null) {
                    final Offre selectedOffre = cardListoffre.get(q);
                    detailsButton.setOnAction(event -> {
                        showOffreDetails(selectedOffre);
                    });
                }


                if (column == 3) { // Ajustement des colonnes
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row);
                grid.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showOffreDetails(Offre selectedOffre) {
        if (selectedOffre != null) {
            typeLabel.setWrapText(true);
            typeLabel.setText(selectedOffre.getType_offre());
            descripLabel.setWrapText(true);
            descripLabel.setText(selectedOffre.getDescription());
            tauxLabel.setText("-" + (int) selectedOffre.getTaux_reduction() + "%");
            dateLabel.setWrapText(true);
            dateLabel.setText("Profitez! \nOffre valable de " + selectedOffre.getDate_debut() + " à " + selectedOffre.getDate_fin());

            // Set the equipment name (assuming you have a way to fetch the name)
            equipeLabel.setWrapText(true);
            equipeLabel.setText(getEquipmentNameById(selectedOffre.getId_equipement()));


            // Load and set the image
            OffreService offreService = new OffreService();
            String imageUrl = offreService.getImageUrlForEquipement(selectedOffre.getId_equipement());
            if (imageUrl != null && !imageUrl.isEmpty()) {
            if (imageUrl.startsWith("http") || imageUrl.startsWith("https")) {
                // Load the image from a URL
                equipImageview.setImage(new Image(imageUrl, 200, 180, false, true));
            } else {

                File file = new File(imageUrl);
                if (file.exists()) {
                    equipImageview.setImage(new Image(file.toURI().toString(), 200, 180, false, true));
                } else {
                    equipImageview.setImage(new Image("file:/path/to/placeholder.png"));
                }
            }
            } else {
                equipImageview.setImage(new Image("file:/path/to/placeholder.png"));
            }
        }
    }

    public String getEquipmentNameById(int equipementId) {
        String equipementNom = "Unknown";
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        String query = "SELECT nom FROM equipement WHERE id = ?";

        try (PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setInt(1, equipementId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                equipementNom = resultSet.getString("nom"); // Get the equipment name
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du nom de l'équipement: " + e.getMessage());
        }

        return equipementNom;
    }




}




