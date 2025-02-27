package controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import utils.MyDb;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;

public class loginuserController implements Initializable {
    @FXML
    private Button CancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private WebView captchaWebView;

    private String captchaToken = null;
    public static String loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File lockFile = new File("images/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
        setupCaptcha();
    }

    private void setupCaptcha() {
        WebEngine webEngine = captchaWebView.getEngine();

        String siteKey = "6LcIu-QqAAAAAJ9E-6LRr9g4o8XPMxok5bZT-2mD";
        String captchaHTML = "<html>" +
                "<head> <title>reCAPTCHA demo: Simple page</title>" +
                "<script src='https://www.google.com/recaptcha/api.js' async defer></script></head>" +
                "<body>" +
                "<form action='?' method='POST'>" +
                "<div class='g-recaptcha' data-sitekey=" + siteKey + "></div>" +
                "<br/>" +
                "<input type='submit' value='Submit'>" +
                "</form>" +
                "</body>" +
                "</html>";





        webEngine.loadContent(captchaHTML);
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaApp", this);
            }
            setCaptchaToken(captchaToken);
        });
    }

    public void setCaptchaToken(String token) {
        this.captchaToken = token;
        System.out.println("CAPTCHA Solved: " + captchaToken);
    }

    public void loginButtonOnAction(ActionEvent event) {
        if (usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()) {
            loginMessageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }
        if (captchaToken == null) {
            loginMessageLabel.setText("Veuillez valider le CAPTCHA.");
            //return;
        }
        validateLogin();
        //verifyCaptchaAndLogin(captchaToken);
    }

    private void verifyCaptchaAndLogin(String token) {
        new Thread(() -> {
            try {
                String secretKey = "6LcIu-QqAAAAABx1uuV2yR98WI0NWcC_XWOCqM-C";
                String postData = "secret=" + URLEncoder.encode(secretKey, "UTF-8") +
                        "&response=" + URLEncoder.encode(token, "UTF-8");

                URL url = new URL("https://api.hcaptcha.com/siteverify");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(postData.getBytes(StandardCharsets.UTF_8));

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String jsonResponseString = reader.readLine();
                System.out.println("hCaptcha API Response: " + jsonResponseString);
                JsonObject jsonResponse = JsonParser.parseString(jsonResponseString).getAsJsonObject();

                boolean success = jsonResponse.get("success").getAsBoolean();

                Platform.runLater(() -> {
                    if (success) {
                        validateLogin();
                    } else {
                        loginMessageLabel.setText("CAPTCHA invalide. Réessayez.");
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> loginMessageLabel.setText("Erreur CAPTCHA !"));
            }
        }).start();
    }

    public void validateLogin() {
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        String verifyLogin = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, enterPasswordField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                loginMessageLabel.setText("Connexion réussie !");
                loggedInUser = queryResult.getString("username");

                String role = queryResult.getString("role");

                if (role.equals("admin")) {
                    try {
                        loginButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
                        Parent root = loader.load();

                        // Pass username to dashboard
                        dashboardController dashboardCtrl = loader.getController();
                        dashboardCtrl.displayUsername();

                        Stage dashboardStage = new Stage();
                        dashboardStage.setScene(new Scene(root, 1100, 600));
                        dashboardStage.initStyle(StageStyle.UNDECORATED);
                        dashboardStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (role.equals("mecanicien") || role.equals("client")) {
                    try {
                        loginButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile.fxml"));
                        Parent root = loader.load();

                        // Pass username to profile
                        profileController profileCtrl = loader.getController();
                        profileCtrl.displayUsername();

                        Stage profileStage = new Stage();
                        profileStage.setScene(new Scene(root, 1100, 600));
                        profileStage.initStyle(StageStyle.UNDECORATED);
                        profileStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                loginMessageLabel.setText("Identifiants invalides. Veuillez réessayer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Erreur de connexion !");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        Stage stage = (Stage) CancelButton.getScene().getWindow();
        try {
            CancelButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login2.fxml"));
            Parent root = loader.load();

            Stage login2Stage = new Stage();
            login2Stage.setScene(new Scene(root, 1100, 600));
            login2Stage.initStyle(StageStyle.DECORATED);
            login2Stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAccountForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUser.fxml"));
            Parent root = loader.load();
            Stage adduserstage = new Stage();
            adduserstage.setScene(new Scene(root, 1100, 660));
            adduserstage.initStyle(StageStyle.UNDECORATED);
            adduserstage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}