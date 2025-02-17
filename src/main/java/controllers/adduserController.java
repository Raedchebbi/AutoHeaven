package controllers;

import models.User;
import services.UserService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import utils.MyDb;

public class adduserController implements Initializable {


    @FXML
    private ImageView shieldImageview;
    @FXML
    private Button fermerButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private TextField setPasswordfield;
    @FXML
    private TextField confirmPasswordfield;

    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField prenomTextfield;
    @FXML
    private TextField nomTextfield;
    @FXML
    private TextField cinTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField telTextfield;
    @FXML
    private TextField adresseTextfield;
    @FXML
    private TextField roleTextfield;
    @FXML
    private TextField usernameTextfield;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("images/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageview.setImage(shieldImage);
    }

    public void inscritButtonOnAction(ActionEvent event) {

        if (setPasswordfield.getText().equals(confirmPasswordfield.getText())) {
            addUser();
            confirmPasswordLabel.setText("");
            registrationMessageLabel.setText("Utilisateur enregistré avec succès !");
        } else {
            confirmPasswordLabel.setText("Le mot de passe ne correspond pas");

        }
    }

    public void fermerButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) fermerButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }


    public void addUser(){
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        String prénom = prenomTextfield.getText();
        String nom = nomTextfield.getText();
        int cin = Integer.parseInt(cinTextfield.getText());
        String email = emailTextfield.getText();
        int tel = Integer.parseInt(telTextfield.getText());
        String adresse = adresseTextfield.getText();
        String role = roleTextfield.getText();
        String username = usernameTextfield.getText();
        String password = setPasswordfield.getText();
        UserService userService = new UserService();
        User newUser = new User( cin, nom, prénom, tel, email, password, role, adresse, username);
        try {
            userService.create(newUser);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

    }

}
