package controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import utils.MyDb;

public class adduserController implements Initializable {

    @FXML
    private ImageView shieldImageview;
    @FXML
    private ImageView photoprofil;
    @FXML
    private Button fermerButton;
    @FXML
    private Button loadphotoBtn;

    @FXML
    private Label errormessage;

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
    private TextField usernameTextfield;

    @FXML
    private TextField photoTextfield;
    @FXML
    private ComboBox<String> su_question;
    @FXML
    private TextField su_answer;

    private UserService userService = new UserService();

    private String[] questionList = {"Quel est le nom de votre premier animal de compagnie ?", "Dans quelle ville êtes-vous né(e) ?", "Quelle est votre couleur préférée ?"};

    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("images/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageview.setImage(shieldImage);
        regLquestionList();
    }

    public void regLquestionList() {
        List<String> listQ = new ArrayList();
        String[] var2 = this.questionList;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        this.su_question.setItems(listData);
    }

    public void inscritButtonOnAction(ActionEvent Event) throws Exception {
        if (!setPasswordfield.getText().equals(confirmPasswordfield.getText())) {
            confirmPasswordLabel.setText("Le mot de passe ne correspond pas");
            return;
        }

        errormessage.setText("");
        confirmPasswordLabel.setText("");

        if (!isValidInput()) {
            return;
        }

        addUser();

        errormessage.setText("Utilisateur enregistré avec succès !");
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> errormessage.setText(""));
        pause.play();
    }


    public void fermerButtonOnAction(ActionEvent event) {
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        Stage stage = (Stage) fermerButton.getScene().getWindow();
        //stage.close();*/

        try {
            fermerButton.getScene().getWindow().hide();
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

    public void addUser() {
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
        String photoProfile = photoTextfield.getText().isEmpty() ? null : photoTextfield.getText();
        String ban = "non";
        String question = su_question.getValue();
        String reponse = su_answer.getText();

        User newUser = new User(cin, nom, prénom, tel, email, password, role, adresse, username, photoProfile, ban, question, reponse);

        try {
            userService.create(newUser);
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void registerUser() throws Exception {
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
        String reponse = su_answer.getText();

        if (!cin.matches("\\d{8}")) {
            errors.append("Erreur : Le CIN doit être composé de 8 chiffres.\n");
        }



        if (!tel.matches("\\d{8}")) {
            errors.append("Erreur : Le numéro de téléphone doit être composé de 8 chiffres.\n");
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.append("Erreur : L'email n'est pas valide.\n");
        }
        if (cin.isBlank() || email.isBlank() || username.isBlank() || password.isBlank() || tel.isBlank() || adresse.isBlank() || reponse.isBlank()) {
            errors.append("Veuillez remplir tous les champs. \n");
        }
        if (userService.cinExists(cin)) {
            errors.append("Ce CIN est déjà utilisé. \n");
        }

        if (userService.usernameExists(username)) {
            errors.append("Ce nom d'utilisateur est déjà pris. \n");
        }


        if (errors.length() > 0) {
            errormessage.setText(errors.toString()); // Display all accumulated errors
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage.setText(""));
            pause.play();
            return false;
        }

        return true;
    }
}
