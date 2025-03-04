package controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
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
import java.util.ArrayList;
import java.util.List;
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
    @FXML
    private ComboBox<String> fp_question;
    @FXML
    private TextField fp_answer;
    @FXML
    private TextField fp_username;
    @FXML
    private AnchorPane fp_questionForm;
    @FXML
    private Button fp_proceedBtn;
    @FXML
    private Button fp_back;
    @FXML
    private AnchorPane si_loginForm;
    @FXML
    private AnchorPane np_newPassForm;
    @FXML
    private PasswordField np_newPassword;
    @FXML
    private PasswordField np_confirmPassword;
    @FXML
    private Button np_changePassBtn;
    @FXML
    private Button np_back;


    private String captchaToken = null;
    public static String loggedInUser;
   public  static int loggedInUserID;
    private String[] questionList = {"Quel est le nom de votre premier animal de compagnie ?", "Dans quelle ville êtes-vous né(e) ?", "Quelle est votre couleur préférée ?"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File lockFile = new File("images/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
        setupCaptcha();
    }

    public void switchForgotPass() {
        this.fp_questionForm.setVisible(true);
        this.si_loginForm.setVisible(false);
        this.forgotPassQuestionList();
    }

    public void changePassBtn() {
        if (!np_newPassword.getText().isEmpty() && !np_confirmPassword.getText().isEmpty()) {
            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
                String updatePass = "UPDATE user SET password = ?, question = ?, reponse = ? WHERE username = ?";

                MyDb connectNow = new MyDb();
                Connection connectDB = connectNow.getConn();

                try {
                    PreparedStatement preparedStatement = connectDB.prepareStatement(updatePass);
                    preparedStatement = connectDB.prepareStatement(updatePass);
                    preparedStatement.setString(1, np_newPassword.getText());
                    preparedStatement.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(3, fp_answer.getText());
                    preparedStatement.setString(4, fp_username.getText());

                    preparedStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed password!");
                    alert.showAndWait();

                    // Reset UI fields
                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);
                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
    }

    public void proceedBtn() {
        if (!this.fp_username.getText().isEmpty() && this.fp_question.getSelectionModel().getSelectedItem() != null && !this.fp_answer.getText().isEmpty()) {
            String selectData = "SELECT username, question, reponse FROM user WHERE username = ? AND question = ? AND reponse = ?";
            MyDb connectNow = new MyDb();
            Connection connectDB = connectNow.getConn();

            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(selectData);
                preparedStatement.setString(1, this.fp_username.getText());
                preparedStatement.setString(2, (String) this.fp_question.getSelectionModel().getSelectedItem());
                preparedStatement.setString(3, this.fp_answer.getText());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }

    }

    public void forgotPassQuestionList() {
        List<String> listQ = new ArrayList();
        String[] var2 = this.questionList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        this.fp_question.setItems(listData);
    }

    public void backToLoginForm() {
        this.si_loginForm.setVisible(true);
        this.fp_questionForm.setVisible(false);
    }

    public void backToQuestionForm() {
        this.fp_questionForm.setVisible(true);
        this.np_newPassForm.setVisible(false);
    }


    private void setupCaptcha() {
        WebEngine webEngine = captchaWebView.getEngine();

        String siteKey = "6LcIu-QqAAAAAJ9E-6LRr9g4o8XPMxok5bZT-2mD";
        String captchaHTML = "<html>" +
                "<head> <title>reCAPTCHA demo: Simple page</title>" +
                "<script src='https://www.google.com/recaptcha/enterprise.js' async defer></script></head>" +
                "<body>" +
                "<form action='?' method='POST'>" +
                "<div class='g-recaptcha' data-sitekey=" + siteKey + "></div>" +
                "<br/>" +
                "<input type='submit' value='Submit'>" +
                "</form>" +
                "</body>" +
                "</html>";
/*"<html lang='fr'>" +
"<head> <meta charset='UTF-8'> <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
  "<title>reCAPTCHA Integration</title>" +
  "<script src="https://www.google.com/recaptcha/api.js" async defer></script>" +
"</head>" +
"<body>" +
"<form action='recaptcha' method='POST'>" +
  "<div class='g-recaptcha' data-sitekey='6LcIu-QqAAAAAJ9E-6LRr9g4o8XPMxok5bZT-2mD'></div>" +
   "<br/>" +
  "<input type='submit' value='Soumettre'>" +
"</form>" +
"</body>" +
"</html>";*/






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
                String banStatus = queryResult.getString("ban");

                // Vérification si l'utilisateur est banni
                if ("Oui".equalsIgnoreCase(banStatus)) {
                    loginMessageLabel.setWrapText(true);
                    loginMessageLabel.setText("Votre compte est temporairement banni. Veuillez réessayer plus tard.");
                    return; // Stop execution if user is banned
                }

                loginMessageLabel.setText("Connexion réussie !");
                loggedInUser = queryResult.getString("username");
                loggedInUserID = queryResult.getInt("id");

                String role = queryResult.getString("role");

                if (role.equals("admin")) {
                    try {
                        loginButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
                        Parent root = loader.load();

                        // Passer le username au dashboard
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

                        // Passer le username au profil
                        profileController profileCtrl = loader.getController();
                        profileCtrl.displayUsername();

                        Stage profileStage = new Stage();
                        profileStage.setScene(new Scene(root, 1180, 600));
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
            adduserstage.initStyle(StageStyle.DECORATED);
            adduserstage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}