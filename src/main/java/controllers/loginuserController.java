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
        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false) {
            loginMessageLabel.setText("tentative de connexion");
            validateLogin();
        }else{
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

        // SQL query with placeholders
        String verifyLogin = "SELECT * FROM `user` WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextField.getText());
            preparedStatement.setString(2, enterPasswordField.getText()); // If using hashed passwords, hash this before setting it.

            ResultSet queryResult = preparedStatement.executeQuery();

            // Check if a user exists
            if (queryResult.next()) {
                loginMessageLabel.setText("Connexion réussie !");
            } else {
                loginMessageLabel.setText("Identifiants invalides. Veuillez réessayer.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Erreur de connexion !");
        }
    }
    public void createAccountForm(){
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUser.fxml"));
            Parent root = loader.load();
            Stage adduserstage = new Stage();
            //Scene sc = new Scene(root);
            adduserstage.setScene(new Scene(root, 520, 665));
            adduserstage.initStyle(StageStyle.UNDECORATED);
            adduserstage.show();

        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
