package controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.User;
import services.UserService;
import utils.MyDb;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File logoFile = new File("images/logo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logo.setImage(logoImage);

        File clientsFile = new File("images/clients.png");
        Image clientsImage = new Image(clientsFile.toURI().toString());
        clients.setImage(clientsImage);

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

        showLoggedInUserDetails();

    }

    public void close() {
        System.exit(0);
    }

    public void displayUsername() {
        usernaame.setText(loggedInUser);
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
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            profile_form.setStyle("-fx-background-color: transparent");
            services_form.setStyle("-fx-background-color: transparent");
            voiture_form.setStyle("-fx-background-color: transparent");
            equip_form.setStyle("-fx-background-color: transparent");
            avis_form.setStyle("-fx-background-color: transparent");
            rec_form.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == profileBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(true);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profile_form.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            services_form.setStyle("-fx-background-color: transparent");
            voiture_form.setStyle("-fx-background-color: transparent");
            equip_form.setStyle("-fx-background-color: transparent");
            avis_form.setStyle("-fx-background-color: transparent");
            rec_form.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == servicesBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(true);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profile_form.setStyle("-fx-background-color: transparent");
            services_form.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            voiture_form.setStyle("-fx-background-color: transparent");
            equip_form.setStyle("-fx-background-color: transparent");
            avis_form.setStyle("-fx-background-color: transparent");
            rec_form.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == VoitureBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(true);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profile_form.setStyle("-fx-background-color: transparent");
            services_form.setStyle("-fx-background-color: transparent");
            voiture_form.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            equip_form.setStyle("-fx-background-color: transparent");
            avis_form.setStyle("-fx-background-color: transparent");
            rec_form.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == equipBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(true);
            avis_form.setVisible(false);
            rec_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profile_form.setStyle("-fx-background-color: transparent");
            services_form.setStyle("-fx-background-color: transparent");
            voiture_form.setStyle("-fx-background-color: transparent");
            equip_form.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            avis_form.setStyle("-fx-background-color: transparent");
            rec_form.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == avisBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(true);
            rec_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profile_form.setStyle("-fx-background-color: transparent");
            services_form.setStyle("-fx-background-color: transparent");
            voiture_form.setStyle("-fx-background-color: transparent");
            equip_form.setStyle("-fx-background-color: transparent");
            avis_form.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            rec_form.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == recBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(true);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profile_form.setStyle("-fx-background-color: transparent");
            services_form.setStyle("-fx-background-color: transparent");
            voiture_form.setStyle("-fx-background-color: transparent");
            equip_form.setStyle("-fx-background-color: transparent");
            avis_form.setStyle("-fx-background-color: transparent");
            rec_form.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
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

            // If input validation fails, stop the execution
            if (!isValidInputUpdate()) {
                return;
            }

            // Create a new UserService instance for updating the user
            UserService userService = new UserService();
            User updatedUser = new User();


            updatedUser.setId(Integer.parseInt(idTextfield1.getText()));
            updatedUser.setCin(Integer.parseInt(cinTextfield1.getText()));  // Set CIN
            updatedUser.setNom(nomTextfield1.getText());
            updatedUser.setPrenom(prenomTextfield1.getText());
            updatedUser.setTel(Integer.parseInt(telTextfield1.getText()));
            updatedUser.setEmail(emailTextfield1.getText());
            updatedUser.setAdresse(adresseTextfield1.getText());  // Set Address
            updatedUser.setUsername(usernameTextfield1.getText());  // Set Username
            updatedUser.setPassword(setPasswordfield1.getText());
            updatedUser.setRole("client");

            // Call the update method to save the changes
            userService.update(updatedUser);

            // Display success message and hide it after a delay
            errormessage.setText("Utilisateur mis à jour avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage.setText(""));
            pause.play();

        } catch (Exception e) {
            // Handle exceptions, including validation failures or database issues
            errormessage.setText("Erreur : " + e.getMessage());
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
