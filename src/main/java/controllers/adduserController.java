package controllers;

import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.util.Duration;
import models.User;
import services.UserService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
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
    private Label errormessage;

    @FXML
    private TextField setPasswordfield;

    @FXML
    private RadioButton roleRadiobtn;

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

    private UserService userService = new UserService();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("images/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageview.setImage(shieldImage);
    }

    public void inscritButtonOnAction(ActionEvent Event) throws Exception {

            // Check if passwords match
            if (!setPasswordfield.getText().equals(confirmPasswordfield.getText())) {
                confirmPasswordLabel.setText("Le mot de passe ne correspond pas");
                return;
            }

            // Clear error messages
            errormessage.setText("");
            confirmPasswordLabel.setText("");

            // Retrieve user input
            String cin = cinTextfield.getText();
            String email = emailTextfield.getText();
            String username = usernameTextfield.getText();
            String password = setPasswordfield.getText();
            String tel = telTextfield.getText();
            String adresse = adresseTextfield.getText();

            // Validate input
            if (!isValidInput()) {
                return;
            }

            // Add user to database
            addUser();

            // Show success message
            errormessage.setText("Utilisateur enregistré avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage.setText(""));
            pause.play();
        }



    public void fermerButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) fermerButton.getScene().getWindow();
        stage.close();
        //Platform.exit();
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
        String role = "client";
        String username = usernameTextfield.getText();
        String password = setPasswordfield.getText();
        User newUser = new User( cin, nom, prénom, tel, email, password, role, adresse, username);
        try {
            userService.create(newUser);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

    }

    public void registerUser() throws Exception {

        String cin = cinTextfield.getText();
        String email = emailTextfield.getText();
        String username = usernameTextfield.getText();
        String password = setPasswordfield.getText();
        String tel = telTextfield.getText();
        String adresse = adresseTextfield.getText();

        errormessage.setText("");

        if (!isValidInput()) {
            return;
        }

        addUser();
        errormessage.setText("Utilisateur enregistré avec succès !");
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> errormessage.setText(""));
        pause.play();
    }

    private boolean isValidInput() throws Exception{
        StringBuilder errors = new StringBuilder();
        String cin = cinTextfield.getText();
        String email = emailTextfield.getText();
        String username = usernameTextfield.getText();
        String password = setPasswordfield.getText();
        String tel = telTextfield.getText();
        String adresse = adresseTextfield.getText();


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
        if (cin.isBlank() || email.isBlank() || username.isBlank() || password.isBlank() || tel.isBlank() || adresse.isBlank() || !roleRadiobtn.isSelected()) {
            errors.append("Veuillez remplir tous les champs. \n");
        }
        if (userService.cinExists(cin)) {
            errors.append("Ce CIN est déjà utilisé. \n");
        }

        if (userService.usernameExists(username)) {
            errors.append("Ce nom d'utilisateur est déjà pris. \n");
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

}
