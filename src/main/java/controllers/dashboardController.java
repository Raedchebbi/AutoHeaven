package controllers;


import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.Duration;
import models.User;
import services.UserService;
import utils.MyDb;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;



public class dashboardController implements Initializable {


    @FXML
    private AnchorPane Acceuil_form;

    @FXML
    private AnchorPane Mecaniciens_form;

    @FXML
    private ImageView acceuil;

    @FXML
    private Button acceuilBtn;

    @FXML
    private Button addMecanicienBtn;

    @FXML
    private ImageView admin;

    @FXML
    private TextField adresseTextfield;

    @FXML
    private TextField adresseTextfield1;

    @FXML
    private ImageView ajout;

    @FXML
    private Button ajouterMecBtn;

    @FXML
    private Label cinLabel;

    @FXML
    private TextField cinTextfield;

    @FXML
    private TextField cinTextfield1;

    @FXML
    private Button readBtn;

    @FXML
    private Button clearBtn2;

    @FXML
    private ImageView clients;

    @FXML
    private Button clientsBtn;

    @FXML
    private AnchorPane clients_form;

    @FXML
    private Button close;

    @FXML
    private Button deleteBtn2;

    @FXML
    private Button deleteMecBtn;

    @FXML
    private ImageView déconnexion;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField emailTextfield1;

    @FXML
    private ImageView employes;

    @FXML
    private Label idLabel;

    @FXML
    private Label errormessage1;


    @FXML
    private Button logout;

    @FXML
    private RadioButton mecRadioBtn;

    @FXML
    private TextField nomTextfield;

    @FXML
    private TextField idTextfield;

    @FXML
    private TextField nomTextfield1;

    @FXML
    private TextField prenomTextfield;

    @FXML
    private TextField prenomTextfield1;

    @FXML
    private PasswordField setPasswordfield;

    @FXML
    private PasswordField setPasswordfield1;

    @FXML
    private VBox tableContainer;

    @FXML
    private VBox tablecontainer;

    @FXML
    private TextField telTextfield;

    @FXML
    private TextField idTextfield1;

    @FXML
    private TextField telTextfield1;

    @FXML
    private Button updateMecBtn;

    @FXML
    private Label usernaame;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField usernameTextfield1;

    @FXML
    private Label errormessage;

    private UserService userService = new UserService();

    public void close(){
        System.exit(0);
    }

    public void showMechanics() throws Exception {
        List<User> allUsers = userService.getAll(); // Get all users
        ObservableList<User> mecaniciens = FXCollections.observableArrayList();



        // Filter users with role "mecanicien"
        for (User user : allUsers) {
            if ("mecanicien".equals(user.getRole())) {
                mecaniciens.add(user);
            }
        }

        // Clear the table container before adding new content
        if (tablecontainer.getChildren().size() > 1) {
            tablecontainer.getChildren().remove(1, tablecontainer.getChildren().size());
        }
        // Loop through the list of mecaniciens to display them in the table
        for (User user : mecaniciens) {
            HBox row = new HBox(20); // Spacing between columns
            Label idLabel = new Label(String.valueOf(user.getId()));
            Label cinLabel = new Label(String.valueOf(user.getCin()));
            Label nomLabel = new Label(user.getNom());
            Label prenomLabel = new Label(user.getPrenom());
            Label telLabel = new Label(String.valueOf(user.getTel()));
            Label emailLabel = new Label(user.getEmail());
            Label passwordLabel = new Label(user.getPassword());
            Label roleLabel = new Label(user.getRole());
            Label adresseLabel = new Label(user.getAdresse());
            Label usernameLabel = new Label(user.getUsername());

            // Set fixed widths for proper alignment
            idLabel.setPrefWidth(50);
            cinLabel.setPrefWidth(130);
            nomLabel.setPrefWidth(130);
            prenomLabel.setPrefWidth(110);
            telLabel.setPrefWidth(120);
            emailLabel.setPrefWidth(200);
            passwordLabel.setPrefWidth(100);
            roleLabel.setPrefWidth(150);
            adresseLabel.setPrefWidth(180);
            usernameLabel.setPrefWidth(120);
            row.getChildren().addAll(
                    idLabel, cinLabel, nomLabel, prenomLabel, telLabel,
                    emailLabel, passwordLabel, roleLabel, adresseLabel, usernameLabel
            );

            tablecontainer.getChildren().add(row);
            row.getStyleClass().add("table-data");
            row.getStyleClass().add("table-row");
            row.setOnMouseClicked(e -> {
                // Retrieve data of the selected mechanic (user)
                showMechanicDetails(user);// Call a method to show or process the selected mechanic's data
            });
        }
    }


    public void showMechanicDetails(User selectedUser) {
        // Set the values of the text fields with the selected mechanic's details
        idTextfield.setText(String.valueOf(selectedUser.getId()));
        cinTextfield.setText(String.valueOf(selectedUser.getCin()));
        nomTextfield.setText(selectedUser.getNom());
        prenomTextfield.setText(selectedUser.getPrenom());
        telTextfield.setText(String.valueOf(selectedUser.getTel()));
        emailTextfield.setText(selectedUser.getEmail());
        adresseTextfield.setText(selectedUser.getAdresse());
        usernameTextfield.setText(selectedUser.getUsername());
        setPasswordfield.setText(selectedUser.getPassword());
        mecRadioBtn.setSelected(true);
    }


    public void addMec(){
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        String prénom = prenomTextfield.getText();
        String nom = nomTextfield.getText();
        int cin = Integer.parseInt(cinTextfield.getText());
        String email = emailTextfield.getText();
        int tel = Integer.parseInt(telTextfield.getText());
        String adresse = adresseTextfield.getText();
        String role = "mecanicien";
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

    public void registerMec() throws Exception {

        String cin = cinTextfield.getText();
        String email = emailTextfield.getText();
        String username = usernameTextfield.getText();
        String password = setPasswordfield.getText();
        String tel = telTextfield.getText();
        String adresse = adresseTextfield.getText();

        // Clear previous error messages
        errormessage.setText("");

        // Use isValidInput() to validate fields and display errors if needed
        if (!isValidInput()) {
            return; // If validation fails, stop the registration process and display all errors
        }
        addMec();

        // Show success message if registration is successful
        errormessage.setText("Utilisateur enregistré avec succès !");
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> errormessage.setText(""));
        pause.play();
    }


    public void handleUpdateAction(ActionEvent Event) {
        try {
            // Establish database connection
            MyDb connectNow = new MyDb();
            Connection connectDB = connectNow.getConn();

            // If input validation fails, stop the execution
            /*if (!isValidInputUpdate()) {
                return;
            }*/


            // Create a new UserService instance for updating the user
            UserService userService = new UserService();
            User updatedUser = new User();

            // Set the ID of the user being updated

            // Set the updated fields from the text fields
            updatedUser.setId(Integer.parseInt(idTextfield.getText()));
            updatedUser.setCin(Integer.parseInt(cinTextfield.getText()));  // Set CIN
            updatedUser.setNom(nomTextfield.getText());  // Set Name
            updatedUser.setPrenom(prenomTextfield.getText());  // Set Surname
            updatedUser.setTel(Integer.parseInt(telTextfield.getText()));  // Set Phone
            updatedUser.setEmail(emailTextfield.getText());  // Set Email
            updatedUser.setAdresse(adresseTextfield.getText());  // Set Address
            updatedUser.setUsername(usernameTextfield.getText());  // Set Username
            updatedUser.setPassword(setPasswordfield.getText());
            updatedUser.setRole("mecanicien");  // Assuming the role is 'mecanicien', change if needed

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

    public void handleDeleteAction(ActionEvent Event) throws Exception {
        try {
            // Retrieve the user ID from the idTextfield
            String idText = idTextfield.getText();

            // If the ID field is empty or invalid, show an error message
            if (idText.isBlank()) {
                errormessage.setText("Veuillez entrer un ID utilisateur.");
                return;
            }

            int userId = Integer.parseInt(idText);  // Convert the ID from text to integer

            // Create a new User object and set the ID
            User userToDelete = new User();
            userToDelete.setId(userId);  // Set the ID of the user to be deleted

            // Create a new UserService instance for deleting the user
            UserService userService = new UserService();

            // Call the delete method to delete the user based on the User object
            userService.delete(userToDelete);

            // Display success message and hide it after a delay
            errormessage.setText("Utilisateur supprimé avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage.setText(""));
            pause.play();

            idTextfield.clear();
            cinTextfield.clear();
            nomTextfield.clear();
            prenomTextfield.clear();
            telTextfield.clear();
            emailTextfield.clear();
            adresseTextfield.clear();
            usernameTextfield.clear();
            setPasswordfield.clear();
            mecRadioBtn.setSelected(false);

        } catch (SQLException e) {
            // Handle database issues or other exceptions
            errormessage.setText("Erreur : " + e.getMessage());
        } catch (NumberFormatException e) {
            // Handle case where the ID is not a valid integer
            errormessage.setText("ID invalide. Veuillez entrer un numéro valide.");
        }
    }
    public void viderOnAction(ActionEvent Event) {
        idTextfield.clear();
        cinTextfield.clear();
        nomTextfield.clear();
        prenomTextfield.clear();
        telTextfield.clear();
        emailTextfield.clear();
        adresseTextfield.clear();
        usernameTextfield.clear();
        setPasswordfield.clear();
        mecRadioBtn.setSelected(false);
    }

    public void displayUsername(){
        usernaame.setText(loginuserController.loggedInUser);
    }

    public void logout(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");
        Optional<ButtonType> option = alert.showAndWait();
        try{
        if (option.get().equals(ButtonType.OK)) {

            logout.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);


            stage.setScene(scene);
            stage.show();
            Platform.exit();
        }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event){
        if(event.getSource() == acceuilBtn){
            Acceuil_form.setVisible(true);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == addMecanicienBtn){
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(true);
            clients_form.setVisible(false);

            addMecanicienBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == clientsBtn){
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(true);

            clientsBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            acceuilBtn.setStyle("-fx-background-color: transparent");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File ajoutFile = new File("images/ajout.png");
        Image ajoutImage = new Image(ajoutFile.toURI().toString());
        ajout.setImage(ajoutImage);

        File employesFile = new File("images/employes.png");
        Image employesImage = new Image(employesFile.toURI().toString());
        employes.setImage(employesImage);

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
        if (cin.isBlank() || email.isBlank() || username.isBlank() || password.isBlank() || tel.isBlank() || adresse.isBlank() || !mecRadioBtn.isSelected()) {
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


    private boolean isValidInputUpdate() throws Exception{
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
        if (cin.isBlank() || email.isBlank() || username.isBlank() || password.isBlank() || tel.isBlank() || adresse.isBlank() || !mecRadioBtn.isSelected()) {
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



    public void showClients() throws Exception {
        List<User> allUsers = userService.getAll(); // Get all users
        ObservableList<User> clients = FXCollections.observableArrayList();


        for (User user : allUsers) {
            if ("client".equals(user.getRole())) {
                clients.add(user);
            }
        }

        // Clear the table container before adding new content
        if (tableContainer.getChildren().size() > 1) {
            tableContainer.getChildren().remove(1, tableContainer.getChildren().size());
        }
        // Loop through the list of mecaniciens to display them in the table
        for (User user : clients) {
            HBox row = new HBox(20); // Spacing between columns
            Label idLabel = new Label(String.valueOf(user.getId()));
            Label cinLabel = new Label(String.valueOf(user.getCin()));
            Label nomLabel = new Label(user.getNom());
            Label prenomLabel = new Label(user.getPrenom());
            Label telLabel = new Label(String.valueOf(user.getTel()));
            Label emailLabel = new Label(user.getEmail());
            Label passwordLabel = new Label(user.getPassword());
            Label adresseLabel = new Label(user.getAdresse());
            Label usernameLabel = new Label(user.getUsername());

            // Set fixed widths for proper alignment
            idLabel.setPrefWidth(50);
            cinLabel.setPrefWidth(140);
            nomLabel.setPrefWidth(130);
            prenomLabel.setPrefWidth(110);
            telLabel.setPrefWidth(120);
            emailLabel.setPrefWidth(200);
            passwordLabel.setPrefWidth(100);
            adresseLabel.setPrefWidth(180);
            usernameLabel.setPrefWidth(120);
            row.getChildren().addAll(
                    idLabel, cinLabel, nomLabel, prenomLabel, telLabel,
                    emailLabel, passwordLabel, adresseLabel, usernameLabel
            );

            tableContainer.getChildren().add(row);
            row.getStyleClass().add("table-data");
            row.getStyleClass().add("table-row");
            row.setOnMouseClicked(e -> {
                // Retrieve data of the selected mechanic (user)
                showClientsDetails(user);// Call a method to show or process the selected mechanic's data
            });
        }
    }

    public void showClientsDetails(User selectedUser) {
        // Set the values of the text fields with the selected mechanic's details
        idTextfield1.setText(String.valueOf(selectedUser.getId()));
        cinTextfield1.setText(String.valueOf(selectedUser.getCin()));
        nomTextfield1.setText(selectedUser.getNom());
        prenomTextfield1.setText(selectedUser.getPrenom());
        telTextfield1.setText(String.valueOf(selectedUser.getTel()));
        emailTextfield1.setText(selectedUser.getEmail());
        adresseTextfield1.setText(selectedUser.getAdresse());
        usernameTextfield1.setText(selectedUser.getUsername());
        setPasswordfield1.setText(selectedUser.getPassword());

    }

    public void handleDeleteActionclient(ActionEvent Event) throws Exception {
        try {

            String idText = idTextfield1.getText();

            // If the ID field is empty or invalid, show an error message
            if (idText.isBlank()) {
                errormessage1.setText("Veuillez entrer un ID utilisateur.");
                return;
            }

            int userId = Integer.parseInt(idText);  // Convert the ID from text to integer

            // Create a new User object and set the ID
            User userToDelete = new User();
            userToDelete.setId(userId);  // Set the ID of the user to be deleted

            // Create a new UserService instance for deleting the user
            UserService userService = new UserService();


            userService.delete(userToDelete);

            // Display success message and hide it after a delay
            errormessage1.setText("Utilisateur supprimé avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage1.setText(""));
            pause.play();

            idTextfield1.clear();
            cinTextfield1.clear();
            nomTextfield1.clear();
            prenomTextfield1.clear();
            telTextfield1.clear();
            emailTextfield1.clear();
            adresseTextfield1.clear();
            usernameTextfield1.clear();
            setPasswordfield1.clear();


        } catch (SQLException e) {
            // Handle database issues or other exceptions
            errormessage1.setText("Erreur : " + e.getMessage());
        } catch (NumberFormatException e) {
            // Handle case where the ID is not a valid integer
            errormessage1.setText("ID invalide. Veuillez entrer un numéro valide.");
        }
    }
    public void viderOnActionclient(ActionEvent Event) {
        idTextfield1.clear();
        cinTextfield1.clear();
        nomTextfield1.clear();
        prenomTextfield1.clear();
        telTextfield1.clear();
        emailTextfield1.clear();
        adresseTextfield1.clear();
        usernameTextfield1.clear();
        setPasswordfield1.clear();
    }



}
