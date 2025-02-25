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
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.User;
import models.Offre;
import services.OffreService;
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


    private ObservableList<Offre> cardListoffre = FXCollections.observableArrayList();

    private double prevX, prevY, prevWidth, prevHeight;
    private boolean isMaximized = false;

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

        File offreFile = new File("images/offre.png");
        Image offreImage = new Image(offreFile.toURI().toString());
        offre.setImage(offreImage);

        File offrelabFile = new File("images/offreLabel.png");
        Image offrelabImage = new Image(offrelabFile.toURI().toString());
        offreLabel.setImage(offrelabImage);

        File offreautoFile = new File("images/offreAutocollant.png");
        Image offreautoImage = new Image(offreautoFile.toURI().toString());
        offreAutocollant.setImage(offreautoImage);


        showLoggedInUserDetails();
        menuDisplayCard();

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
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == profileBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(true);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);

            profileBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == servicesBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(true);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == VoitureBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(true);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");



        } else if (event.getSource() == equipBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(true);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            equipBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");



        } else if (event.getSource() == avisBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(true);
            rec_form.setVisible(false);
            offre_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");



        } else if (event.getSource() == recBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(true);
            offre_form.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            OffreBtn.setStyle("-fx-background-color: transparent");


        } else if (event.getSource() == OffreBtn) {
            Acceuil_form.setVisible(false);
            profile_form.setVisible(false);
            services_form.setVisible(false);
            voiture_form.setVisible(false);
            equip_form.setVisible(false);
            avis_form.setVisible(false);
            rec_form.setVisible(false);
            offre_form.setVisible(true);

            acceuilBtn.setStyle("-fx-background-color: transparent");
            profileBtn.setStyle("-fx-background-color: transparent");
            servicesBtn.setStyle("-fx-background-color: transparent");
            VoitureBtn.setStyle("-fx-background-color: transparent");
            equipBtn.setStyle("-fx-background-color: transparent");
            avisBtn.setStyle("-fx-background-color: transparent");
            recBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
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
            userService.update(updatedUser);

            // Display success message
            usernaame.setText(loggedInUser);
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




