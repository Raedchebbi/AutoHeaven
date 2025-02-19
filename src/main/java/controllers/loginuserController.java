package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import utils.MyDb;


import java.io.File;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;

public class loginuserController implements Initializable {
    @FXML
    private Button CancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;

    // Static variable to store the logged-in username
    public static String loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("images/3.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("images/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void loginButtonOnAction(ActionEvent event) {
        loginMessageLabel.setText("try to login");
        if (!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
            loginMessageLabel.setText("tentative de connexion");
            validateLogin();
        } else {
            loginMessageLabel.setText("Veuillez remplir tous les champs.");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        String verifyLogin = "SELECT * FROM `user` WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, enterPasswordField.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next()) {
                loginMessageLabel.setText("Connexion réussie !");
                loggedInUser = queryResult.getString("username"); // Store username

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
                }
            } else {
                loginMessageLabel.setText("Identifiants invalides. Veuillez réessayer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Erreur de connexion !");
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
