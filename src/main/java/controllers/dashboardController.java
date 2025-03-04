package controllers;


import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.util.Pair;
import models.CamionRemorquage;
import models.EquipementAffichage;
import models.Offre;
import models.User;
import services.CamionRemorquageService;
import services.UserService;
import services.OffreService;
import utils.MyDb;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class dashboardController implements Initializable {


    @FXML
    private AnchorPane Acceuil_form;

    @FXML
    private DatePicker DateFpick;

    @FXML
    private AnchorPane Mecaniciens_form;

    @FXML
    private Button OffreBtn;

    @FXML
    private AnchorPane Offre_form;



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
    private Button ajouterOffreBtn;

    @FXML
    private TextField cinTextfield;

    @FXML
    private TextField cinTextfield1;

    @FXML
    private Button clearBtn;

    @FXML
    private Button clearBtn2;

    @FXML
    private Button clearBtn3;

    @FXML
    private ImageView clients;


    @FXML
    private Button clientsBtn;

    @FXML
    private AnchorPane clients_form;

    @FXML
    private Button close;

    @FXML
    private DatePicker dateDpick;

    @FXML
    private Button deleteBtn2;

    @FXML
    private Button deleteBtn3;

    @FXML
    private Button deleteMecBtn;

    @FXML
    private TextField descTextfield;

    @FXML
    private ImageView déconnexion;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField emailTextfield1;

    @FXML
    private ImageView employes;

    @FXML
    private ImageView offre;

    @FXML
    private ComboBox<Pair<Integer, String>> equipCombobox;

    @FXML
    private Label errormessage;

    @FXML
    private Label errormessage1;

    @FXML
    private Label errormessage3;

    @FXML
    private TextField idTextfield;

    @FXML
    private TextField idTextfield1;

    @FXML
    private TextField idTextfield3;

    @FXML
    private ImageView imageOffre;

    @FXML
    private Button loadphotoBtn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private RadioButton mecRadioBtn;

    @FXML
    private TextField nomTextfield;

    @FXML
    private TextField nomTextfield1;

    @FXML
    private TextField prenomTextfield;

    @FXML
    private TextField prenomTextfield1;

    @FXML
    private Button readBtn2;

    @FXML
    private PasswordField setPasswordfield;

    @FXML
    private PasswordField setPasswordfield1;

    @FXML
    private VBox tableContainer;

    @FXML
    private VBox tableContainer1;

    @FXML
    private VBox tablecontainer;

    @FXML
    private TextField tauxTextfield;

    @FXML
    private TextField telTextfield;

    @FXML
    private TextField telTextfield1;

    @FXML
    private TextField typeTextfield;

    @FXML
    private Button updateMecBtn;

    @FXML
    private Button updateOffreBtn;

    @FXML
    private Label usernaame;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField usernameTextfield1;
    @FXML
    private Button combtn;

    @FXML
    private AnchorPane comform;
    @FXML
    private AnchorPane equipement_form;
    @FXML
    Button equipbtn;
    @FXML
    private ListEquipement listEquipementController;
    @FXML
    private ListEquipementClient listc;
    @FXML
    private ValidationCommande commandeController;

    @FXML
    private DetailEquipement dd;
    @FXML
    private AnchorPane pan_form;
    @FXML
    private Button pan ;
    @FXML
    private AnchorPane rec_form;
    @FXML
    private Button recbtn;
    @FXML
    private ComboBox<String> banCombobox;

    @FXML
    private ComboBox<String> banCombobox1;

    @FXML
    private Button affichercatBtn;
    @FXML
    private Button afficherVBtn;
    @FXML
    private Button addCatBtn;
    @FXML
    private Button addVBtn;
    @FXML
    private Button voitureBtn;
    @FXML
    private AnchorPane categorie_form;
    @FXML
    private AnchorPane voiture_form;
    @FXML
    private AnchorPane addcat_form;
    @FXML
    private AnchorPane addv_form;
    @FXML
    private AnchorPane voiture_form1;





    @FXML
    private Button camionbtn;
    @FXML
    private Button rdvmecbtn;
    @FXML
    private Button remobtn;
    @FXML
    private Button testdbtn;

    @FXML
    private AnchorPane camion_form;
    @FXML
    private AnchorPane rdvmec_form;
    @FXML
    private AnchorPane remo_form;
    @FXML
    private AnchorPane testd_form;






    private String[] banList = {"oui", "non"};

    private Image image;

    public Button getPan() {
        return pan;
    }

    private UserService userService = new UserService();
    private OffreService offreService = new OffreService();

    private Statistique statcon ;

    @FXML
    AfficherReclamationController aff = new AfficherReclamationController();
    @FXML
    affichercategorie cat = new affichercategorie();
    @FXML
    affichervoiture cv = new affichervoiture();
    @FXML
    addcategorie c = new addcategorie();
    @FXML
    Addvoiture cv1 = new Addvoiture();

    public void regLbanList() {
        List<String> listQ = new ArrayList();
        String[] var2 = this.banList;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String data = var2[var4];
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        this.banCombobox.setItems(listData);
        this.banCombobox1.setItems(listData);
    }


    public void close(){
        System.exit(0);
    }

    public void showMechanics() throws Exception {
        List<User> allUsers = userService.getAll(); // Get all users
        ObservableList<User> mecaniciens = FXCollections.observableArrayList();

        for (User user : allUsers) {
            if ("mecanicien".equals(user.getRole())) {
                mecaniciens.add(user);
            }
        }

        if (tablecontainer.getChildren().size() > 1) {
            tablecontainer.getChildren().remove(1, tablecontainer.getChildren().size());
        }
        for (User user : mecaniciens) {
            HBox row = new HBox(20); // Spacing between columns

            Label cinLabel = new Label(String.valueOf(user.getCin()));
            Label nomLabel = new Label(user.getNom());
            Label prenomLabel = new Label(user.getPrenom());
            Label telLabel = new Label(String.valueOf(user.getTel()));
            Label emailLabel = new Label(user.getEmail());
            Label passwordLabel = new Label(user.getPassword());
            Label roleLabel = new Label(user.getRole());
            Label adresseLabel = new Label(user.getAdresse());
            adresseLabel.setWrapText(true);
            adresseLabel.setMaxWidth(180);
            Label usernameLabel = new Label(user.getUsername());
            Label baniLabel = new Label(user.getBan());


            cinLabel.setPrefWidth(130);
            nomLabel.setPrefWidth(130);
            prenomLabel.setPrefWidth(110);
            telLabel.setPrefWidth(120);
            emailLabel.setPrefWidth(200);
            passwordLabel.setPrefWidth(100);
            roleLabel.setPrefWidth(50);
            adresseLabel.setPrefWidth(180);
            usernameLabel.setPrefWidth(120);
            baniLabel.setPrefWidth(100);
            row.getChildren().addAll(
                    cinLabel, nomLabel, prenomLabel, telLabel,
                    emailLabel, passwordLabel, roleLabel, adresseLabel, usernameLabel, baniLabel
            );

            tablecontainer.getChildren().add(row);
            row.getStyleClass().add("table-data");
            row.getStyleClass().add("table-row");
            row.setOnMouseClicked(e -> {

                showMechanicDetails(user);
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
        banCombobox1.setValue(selectedUser.getBan());
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
        String photoProfile = null;
        String ban = banCombobox1.getValue();
        String question = null;
        String reponse = null;

        UserService userService = new UserService();
        User newUser = new User(cin, nom, prénom, tel, email, password, role, adresse, username, photoProfile, ban, question, reponse);
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
        String ban = banCombobox1.getValue();

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
            updatedUser.setRole("mecanicien");
            updatedUser.setBan(banCombobox1.getValue());

            // Call the update method to save the changes
            userService.update(updatedUser);

            // Display success message and hide it after a delay
            errormessage.setText("Utilisateur mis à jour avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage.setText(""));
            pause.play();
            showMechanics();

        } catch (Exception e) {
            // Handle exceptions, including validation failures or database issues
            errormessage.setText("Erreur : " + e.getMessage());
        }
    }

    public void handleBannirAction(ActionEvent event) {
        try {
            // Création d'un nouvel objet User basé sur les champs actuels
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

            // Bannir l'utilisateur temporairement
            updatedUser.setBan("oui");
            userService.update(updatedUser);
            banCombobox.setValue("oui");
            showClients();

            // Affichage du message

            errormessage1.setText("L'utilisateur est banni pour une durée limitée. Il sera débanni automatiquement après quelques secondes.");
            System.out.println("Utilisateur banni : Oui");
            errormessage1.setWrapText(true);
            PauseTransition pause1 = new PauseTransition(Duration.seconds(5));
            pause1.setOnFinished(e -> errormessage1.setText(""));
            pause1.play();


            PauseTransition pause = new PauseTransition(Duration.seconds(1000));
            pause.setOnFinished(e -> {
                try {
                    updatedUser.setBan("non");
                    userService.update(updatedUser);
                    banCombobox.setValue("non");
                    errormessage1.setText("L'utilisateur a été débanni.");
                    System.out.println("Utilisateur débanni : Non");
                    showClients();
                } catch (Exception ex) {
                    errormessage1.setWrapText(true);
                    errormessage1.setText("Erreur lors du débannissement : " + ex.getMessage());
                }
            });
            pause.play();

        } catch (Exception e) {
            errormessage1.setText("Erreur : " + e.getMessage());
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
            showMechanics();

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
            banCombobox1.setValue(null);

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
        banCombobox1.setValue(null);
    }

    public void displayUsername(){
        usernaame.setText(loginuserController.loggedInUser);
    }

    public void logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");
        Optional<ButtonType> option = alert.showAndWait();
        try{
            if (option.get().equals(ButtonType.OK)) {

                // Load the login interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginUser.fxml"));
                Parent root = loader.load();

                // Get current stage (window) and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void switchFormraed(ActionEvent event) {
        if (event.getSource() == affichercatBtn) {
            categorie_form.setVisible(true);
            voiture_form.setVisible(false);
            addcat_form.setVisible(false);
            addv_form.setVisible(false);



        } else if (event.getSource() == afficherVBtn) {
            categorie_form.setVisible(false);
            voiture_form.setVisible(true);
            addcat_form.setVisible(false);
            addv_form.setVisible(false);


        }  else if (event.getSource() == addCatBtn) {
            categorie_form.setVisible(false);
            voiture_form.setVisible(false);
            addcat_form.setVisible(true);
            addv_form.setVisible(false);


        }  else if (event.getSource() == addVBtn) {
            categorie_form.setVisible(false);
            voiture_form.setVisible(false);
            addcat_form.setVisible(false);
            addv_form.setVisible(true);


        }
    }

            public void switchForm(ActionEvent event) {
        if (event.getSource() == acceuilBtn) {
            Acceuil_form.setVisible(true);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            acceuilBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            recbtn.setStyle("-fx-background-color: transparent");
            camionbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == addMecanicienBtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(true);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            addMecanicienBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            recbtn.setStyle("-fx-background-color: transparent");
            camionbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == clientsBtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(true);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            clientsBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            recbtn.setStyle("-fx-background-color: transparent");
            camionbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == OffreBtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(true);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            OffreBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            clientsBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            recbtn.setStyle("-fx-background-color: transparent");
            camionbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == equipbtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(true);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            equipbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            recbtn.setStyle("-fx-background-color: transparent");
            camionbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == combtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(true);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            combtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            recbtn.setStyle("-fx-background-color: transparent");
            camionbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == recbtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(true);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            recbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            camionbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));







        } else if (event.getSource() == camionbtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(true);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            camionbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            recbtn.setStyle("-fx-background-color: transparent");
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == rdvmecbtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(true);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            rdvmecbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle(("-fx-background-color: transparent"));
            clientsBtn.setStyle(("-fx-background-color: transparent"));
            OffreBtn.setStyle(("-fx-background-color: transparent"));
            equipbtn.setStyle(("-fx-background-color: transparent"));
            combtn.setStyle(("-fx-background-color: transparent"));
            recbtn.setStyle(("-fx-background-color: transparent"));
            camionbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == remobtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(true);
            testd_form.setVisible(false);
            voiture_form1.setVisible(false);

            remobtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle(("-fx-background-color: transparent"));
            clientsBtn.setStyle(("-fx-background-color: transparent"));
            OffreBtn.setStyle(("-fx-background-color: transparent"));
            equipbtn.setStyle(("-fx-background-color: transparent"));
            combtn.setStyle(("-fx-background-color: transparent"));
            recbtn.setStyle(("-fx-background-color: transparent"));
            camionbtn.setStyle(("-fx-background-color: transparent"));
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));
            voitureBtn.setStyle(("-fx-background-color: transparent"));


        } else if (event.getSource() == voitureBtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(false);
            voiture_form1.setVisible(true);

            voitureBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle(("-fx-background-color: transparent"));
            clientsBtn.setStyle(("-fx-background-color: transparent"));
            OffreBtn.setStyle(("-fx-background-color: transparent"));
            equipbtn.setStyle(("-fx-background-color: transparent"));
            combtn.setStyle(("-fx-background-color: transparent"));
            recbtn.setStyle(("-fx-background-color: transparent"));
            camionbtn.setStyle(("-fx-background-color: transparent"));
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
            testdbtn.setStyle(("-fx-background-color: transparent"));

        } else if (event.getSource() == testdbtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            rec_form.setVisible(false);
            camion_form.setVisible(false);
            rdvmec_form.setVisible(false);
            remo_form.setVisible(false);
            testd_form.setVisible(true);

            testdbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle(("-fx-background-color: transparent"));
            clientsBtn.setStyle(("-fx-background-color: transparent"));
            OffreBtn.setStyle(("-fx-background-color: transparent"));
            equipbtn.setStyle(("-fx-background-color: transparent"));
            combtn.setStyle(("-fx-background-color: transparent"));
            recbtn.setStyle(("-fx-background-color: transparent"));
            camionbtn.setStyle(("-fx-background-color: transparent"));
            rdvmecbtn.setStyle(("-fx-background-color: transparent"));
            remobtn.setStyle(("-fx-background-color: transparent"));
        }






    }

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

        File offreFile = new File("images/offre.png");
        Image offreImage = new Image(offreFile.toURI().toString());
        offre.setImage(offreImage);



        try {
            showMechanics();
            showClients();
            showOffres();
            loadEquipements();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
            Parent listEquipement = loader.load();
            listEquipementController = loader.getController();
            equipement_form.getChildren().add(listEquipement);
            listEquipementController.setDashboardController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ValidationCommande.fxml"));
            Parent listCommandes = loader.load();
            commandeController = loader.getController();
            comform.getChildren().add(listCommandes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationController.fxml"));
            Parent listrec = loader.load();
            aff = loader.getController();
            rec_form.getChildren().add(listrec);
            aff.setDashboardController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichercategorie.fxml"));
            Parent cate = loader.load();
            cat = loader.getController();
            categorie_form.getChildren().add(cate);
            cat.setDashboardController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichervoiture.fxml"));
            Parent voit = loader.load();
            cv = loader.getController();
            voiture_form.getChildren().add(voit);
            cv.setDashboardController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addcategorie.fxml"));
            Parent addcat = loader.load();
            c = loader.getController();
            addcat_form.getChildren().add(addcat);
            c.setDashboardController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addvoiture.fxml"));
            Parent addv = loader.load();
            cv1 = loader.getController();
            addv_form.getChildren().add(addv);
            cv1.setDashboardController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }







        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewCamionRemorquage.fxml"));
            Parent listCamion = loader.load();

            ViewCamionRemorquageController camionController = loader.getController();
            camionController.setDashboardController(this); // Pass the dashboard controller

            camion_form.getChildren().clear();
            camion_form.getChildren().add(listCamion);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de ViewCamionRemorquage.fxml");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewMecanicienRDV.fxml"));
            Parent listMecRDV = loader.load();

            ViewMecanicienRDVController mecrdvController = loader.getController();
            mecrdvController.setDashboardController(this); // Pass the dashboard controller

            rdvmec_form.getChildren().clear();
            rdvmec_form.getChildren().add(listMecRDV);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de ViewMecanicienRDV.fxml");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewRemorquage.fxml"));
            Parent listRemo = loader.load();

            ViewRemorquageController remoController = loader.getController();
            remoController.setDashboardController(this); // Pass the dashboard controller

            remo_form.getChildren().clear();
            remo_form.getChildren().add(listRemo);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de ViewRemorquage.fxml");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewTestDrive.fxml"));
            Parent listTestD = loader.load();

            ViewTestDriveController testdController = loader.getController();
            testdController.setDashboardController(this); // Pass the dashboard controller

            testd_form.getChildren().clear();
            testd_form.getChildren().add(listTestD);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de ViewTestDrive.fxml");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Statistique.fxml"));
            Parent stat = loader.load();
            statcon = loader.getController();
            Acceuil_form.getChildren().add(stat);
        } catch (IOException e) {
            e.printStackTrace();
        }










    }

    public void loadListEquipementForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
            Parent listEquipement = loader.load();
            listEquipementController = loader.getController();
            listEquipementController.setDashboardController(this);

            // Assurez-vous que la vue ListEquipement est ajoutée au bon conteneur dans le Dashboard
            equipement_form.getChildren().clear();
            equipement_form.getChildren().add(listEquipement);

            // Masquer les autres formulaires et afficher uniquement equipement_form
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(true);
            comform.setVisible(false);

            // Mettre à jour le style du bouton pour indiquer que le formulaire est actif
            equipbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (!cin.matches("\\d{8}")) {
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

        // Loop through the list of clients to display them in the table
        for (User user : clients) {
            HBox row = new HBox(20); // Spacing between columns
            Label cinLabel = new Label(String.valueOf(user.getCin()));
            Label nomLabel = new Label(user.getNom());
            Label prenomLabel = new Label(user.getPrenom());
            Label telLabel = new Label(String.valueOf(user.getTel()));
            Label emailLabel = new Label(user.getEmail());
            Label passwordLabel = new Label(user.getPassword());
            Label adresseLabel = new Label(user.getAdresse());
            Label usernameLabel = new Label(user.getUsername());
            Label banLabel = new Label(user.getBan()); // Display ban status as label

            // Set fixed widths for proper alignment
            cinLabel.setPrefWidth(130);
            nomLabel.setPrefWidth(130);
            prenomLabel.setPrefWidth(110);
            telLabel.setPrefWidth(120);
            emailLabel.setPrefWidth(200);
            passwordLabel.setPrefWidth(100);
            adresseLabel.setPrefWidth(180);
            usernameLabel.setPrefWidth(120);
            banLabel.setPrefWidth(100);

            row.getChildren().addAll(
                    cinLabel, nomLabel, prenomLabel, telLabel,
                    emailLabel, passwordLabel, adresseLabel, usernameLabel, banLabel
            );

            tableContainer.getChildren().add(row);
            row.getStyleClass().add("table-data");
            row.getStyleClass().add("table-row");
            row.setOnMouseClicked(e -> {
                // Retrieve data of the selected client (user)
                showClientsDetails(user);// Call a method to show or process the selected client's data
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
        banCombobox.setValue(selectedUser.getBan());

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
            showClients();

            idTextfield1.clear();
            cinTextfield1.clear();
            nomTextfield1.clear();
            prenomTextfield1.clear();
            telTextfield1.clear();
            emailTextfield1.clear();
            adresseTextfield1.clear();
            usernameTextfield1.clear();
            setPasswordfield1.clear();
            banCombobox.setValue(null);


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
        banCombobox.setValue(null);
    }



    public void showOffres() throws Exception {
        List<Offre> allOffres = offreService.getAll(); // Get all offers
        ObservableList<Offre> offresList = FXCollections.observableArrayList();
        offresList.addAll(allOffres);

        // Clear the table container before adding new content
        if (tableContainer1.getChildren().size() > 1) {
            tableContainer1.getChildren().remove(1, tableContainer1.getChildren().size());
        }

        // Loop through the list of offers to display them in the table
        for (Offre offre : offresList) {
            HBox row = new HBox(20); // Spacing between columns

            Label typeLabel = new Label(offre.getType_offre());
            Label descLabel = new Label(offre.getDescription());
            Label tauxLabel = new Label(offre.getTaux_reduction() + "%") ;
            Label debutLabel = new Label(offre.getDate_debut());
            Label finLabel = new Label(offre.getDate_fin());
            String equipementNom = getEquipementNom(offre.getId_equipement());
            Label equipLabel = new Label(equipementNom);


            typeLabel.setPrefWidth(120);
            descLabel.setPrefWidth(250);
            tauxLabel.setPrefWidth(50);
            debutLabel.setPrefWidth(100);
            finLabel.setPrefWidth(100);
            equipLabel.setPrefWidth(150);


            row.getChildren().addAll(
                    typeLabel, descLabel, tauxLabel,
                    debutLabel, finLabel, equipLabel
            );

            tableContainer1.getChildren().add(row);
            row.getStyleClass().add("table-data");
            row.getStyleClass().add("table-row");

            row.setOnMouseClicked(e -> {
                // Handle the click event for showing offer details
                showOffreDetails(offre);
            });
        }
    }
    public String getEquipementNom(int equipementId) {
        String equipementNom = "Unknown"; // Default in case of error
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
    private int getEquipementIdByName(String equipementName) {
        try {
            Connection conn = MyDb.getInstance().getConn();
            String query = "SELECT id FROM equipement WHERE nom = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, equipementName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public void showOffreDetails(Offre selectedOffre) {

        idTextfield3.setText(String.valueOf(selectedOffre.getId_offre()));
        typeTextfield.setText(selectedOffre.getType_offre());
        descTextfield.setText(selectedOffre.getDescription());
        tauxTextfield.setText(String.valueOf(selectedOffre.getTaux_reduction()));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debutDate = LocalDate.parse(selectedOffre.getDate_debut(), formatter);
        LocalDate finDate = LocalDate.parse(selectedOffre.getDate_fin(), formatter);
        dateDpick.setValue(debutDate); // Set the date in the DatePicker for debut
        DateFpick.setValue(finDate);

        int selectedEquipementId = selectedOffre.getId_equipement();
        for (Pair<Integer, String> equip : equipCombobox.getItems()) {
            if (equip.getKey() == selectedEquipementId) {
                equipCombobox.getSelectionModel().select(equip);
                break;
            }
        }

    }

    public void loadEquipements() {
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        ObservableList<Pair<Integer, String>> equipementList = FXCollections.observableArrayList(); // Pair<ID, Name>

        String query = "SELECT id, nom FROM equipement"; // Change "nom" if the column name is different

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nom");
                equipementList.add(new Pair<>(id, name)); // Store ID and Name
            }

            equipCombobox.setItems(equipementList);

            // Convert Pair to only display names
            equipCombobox.setConverter(new StringConverter<Pair<Integer, String>>() {
                @Override
                public String toString(Pair<Integer, String> equip) {
                    return equip != null ? equip.getValue() : "";
                }

                @Override
                public Pair<Integer, String> fromString(String string) {
                    return null; // Not needed
                }
            });

        } catch (Exception e) {
            System.out.println("Erreur de chargement des équipements : " + e.getMessage());
        }
    }



    public void addOffre() {
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        String type_offre = typeTextfield.getText();
        String description = descTextfield.getText();
        double taux_reduction = Double.parseDouble(tauxTextfield.getText());

        // Get selected dates from DatePickers
        String date_debut = dateDpick.getValue().toString();
        String date_fin = DateFpick.getValue().toString();


        // Get selected equipment ID from ComboBox (assuming it contains Pair<Integer, String>)
        Pair<Integer, String> selectedEquipement = equipCombobox.getValue();
        int id_equipement = (selectedEquipement != null) ? selectedEquipement.getKey() : -1;
        if (id_equipement == -1) {
            errormessage.setText("Équipement non valide.");
            return;
        }
        int id_equip = selectedEquipement.getKey();

        // Fetch the image URL for the selected equipment
        OffreService offreService = new OffreService();
        String imageUrl = offreService.getImageUrlForEquipement(id_equip);

        if (imageUrl == null) {
            errormessage.setText("Aucune image trouvée pour cet équipement.");
            return;
        }

        Offre newOffre = new Offre(type_offre, description, taux_reduction, date_debut, date_fin, id_equipement, imageUrl);

        try {
            offreService.create(newOffre);
            System.out.println("Offre ajoutée avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }


    public void registerOffre() throws Exception {
        String typeOffre = typeTextfield.getText();
        String description = descTextfield.getText();
        String tauxReductionStr = tauxTextfield.getText();
        LocalDate dateDebut = dateDpick.getValue();
        LocalDate dateFin = DateFpick.getValue();
        Pair<Integer, String> selectedEquipementPair = equipCombobox.getValue();


        errormessage3.setText("");


        if (!isValidInputOffre()) {
            return; // Stop registration if validation fails
        }
        addOffre();
        errormessage3.setText("Utilisateur enregistré avec succès !");
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> errormessage3.setText(""));
        pause.play();
        showOffres();

    }

    private boolean isValidInputOffre() throws Exception {
        StringBuilder errors = new StringBuilder();

        String typeOffre = typeTextfield.getText();
        String description = descTextfield.getText();
        String tauxReductionStr = tauxTextfield.getText();
        String dateDebut = dateDpick.getValue() != null ? dateDpick.getValue().toString() : "";
        String dateFin = DateFpick.getValue() != null ? DateFpick.getValue().toString() : "";
        Pair<Integer, String> selectedEquipement = equipCombobox.getValue();

        // Validate that no field is empty
        if (typeOffre.isBlank() || description.isBlank() || tauxReductionStr.isBlank() || dateDebut.isBlank() || dateFin.isBlank()) {
            errors.append("Veuillez remplir tous les champs. \n");
        }

        if (errors.length() > 0) {
            errormessage3.setText(errors.toString()); // Display all accumulated errors
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage3.setText(""));
            pause.play();
            return false;  // Validation failed
        }

        if (selectedEquipement == null) {
            errors.append("Veuillez sélectionner un équipement. \n");
        }

        // Validate that taux_reduction is a valid double
        try {
            Double.parseDouble(tauxReductionStr);
        } catch (NumberFormatException e) {
            errors.append("Erreur : Le taux de réduction doit être un nombre valide. \n");
        }

        // Validate that date_fin is not before date_debut
        try {
            LocalDate debutDate = LocalDate.parse(dateDebut);
            LocalDate finDate = LocalDate.parse(dateFin);
            if (finDate.isBefore(debutDate)) {
                errors.append("Erreur : La date de fin ne peut pas être avant la date de début. \n");
            }
        } catch (Exception e) {
            errors.append("Erreur : Les dates doivent être valides. \n");
        }



        // If there are any errors, display them all at once
        if (errors.length() > 0) {
            errormessage3.setText(errors.toString()); // Display all accumulated errors
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage3.setText(""));
            pause.play();
            return false;  // Validation failed
        }

        return true;  // Validation passed
    }

    public void handleUpdateActionOffre(ActionEvent Event) {
        try {
            // Establish database connection
            MyDb connectNow = new MyDb();
            Connection connectDB = connectNow.getConn();

            // If input validation fails, stop the execution (validation code can be uncommented if needed)
            // if (!isValidInputUpdate()) {
            //     return;
            // }

            // Create a new OffreService instance for updating the offer
            OffreService offreService = new OffreService();
            Offre updatedOffre = new Offre();

            // Set the ID of the offer being updated
            updatedOffre.setId_offre(Integer.parseInt(idTextfield3.getText())); // Set ID from the textfield

            // Set the updated fields from the respective text fields
            updatedOffre.setType_offre(typeTextfield.getText());  // Set type of offer
            updatedOffre.setDescription(descTextfield.getText());  // Set description
            updatedOffre.setTaux_reduction(Double.parseDouble(tauxTextfield.getText()));  // Set discount rate

            // Ensure date fields are not null before parsing
            String dateDebut = dateDpick.getValue() != null ? dateDpick.getValue().toString() : null;
            String dateFin = DateFpick.getValue() != null ? DateFpick.getValue().toString() : null;

            if (dateDebut != null && dateFin != null) {
                updatedOffre.setDate_debut(dateDebut);  // Set start date
                updatedOffre.setDate_fin(dateFin);  // Set end date
            } else {
                errormessage3.setText("Erreur : Les dates de début et de fin doivent être remplies.");
                return;
            }

            // Get selected equipment ID from ComboBox (assuming it contains Pair<Integer, String>)
            Pair<Integer, String> selectedEquipement = equipCombobox.getValue();
            int idEquipement = (selectedEquipement != null) ? selectedEquipement.getKey() : -1;

            if (idEquipement == -1) {
                errormessage.setText("Erreur : Équipement non valide.");
                return;
            }

            updatedOffre.setId_equipement(idEquipement);  // Set the equipment ID

            // Call the update method in OffreService to save the changes
            offreService.update(updatedOffre);

            // Display success message and hide it after a delay
            errormessage3.setText("Offre mise à jour avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage3.setText(""));
            pause.play();
            showOffres();

            // Optional: Refresh display after update (for example, calling a method to reload the offer list)
            // showOffers(); // (Uncomment if you want to reload the offers list after updating)

        } catch (Exception e) {
            // Handle exceptions, including validation failures or database issues
            errormessage3.setText("Erreur : " + e.getMessage());
        }
    }


    public void handleDeleteActionOffre(ActionEvent Event) throws Exception {
        try {
            // Retrieve the ID from the idTextfield
            String idText = idTextfield3.getText();

            // If the ID field is empty or invalid, show an error message
            if (idText.isBlank()) {
                errormessage3.setText("Veuillez entrer un ID offre.");
                return;
            }

            int offreId = Integer.parseInt(idText);  // Convert the ID from text to integer

            // Create a new Offre object and set the ID
            Offre offreToDelete = new Offre();
            offreToDelete.setId_offre(offreId);  // Set the ID of the offre to be deleted

            // Create a new OffreService instance for deleting the offre
            OffreService offreService = new OffreService();

            // Call the delete method to delete the offre based on the Offre object
            offreService.delete(offreToDelete);

            // Display success message and hide it after a delay
            errormessage3.setText("Offre supprimée avec succès !");
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> errormessage3.setText(""));
            pause.play();
            showOffres();  // Assuming this method displays updated offers

            // Clear fields after deletion
            idTextfield3.clear();
            typeTextfield.clear();
            descTextfield.clear();
            tauxTextfield.clear();
            dateDpick.setValue(null);
            DateFpick.setValue(null);
            equipCombobox.getSelectionModel().clearSelection();
            showOffres();


        } catch (SQLException e) {
            errormessage.setText("Erreur : " + e.getMessage());
        } catch (NumberFormatException e) {
            // Handle case where the ID is not a valid integer
            errormessage.setText("ID invalide. Veuillez entrer un numéro valide.");
        }
    }


    public void viderOnActionOffre(ActionEvent Event) {
        idTextfield3.clear();
        typeTextfield.clear();
        descTextfield.clear();
        tauxTextfield.clear();
        dateDpick.setValue(null);  // Clear date_debut DatePicker
        DateFpick.setValue(null);    // Clear date_fin DatePicker
        equipCombobox.getSelectionModel().clearSelection();  // Clear ComboBox selection
    }

    public void goToReservationTestDrive(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewTestDrive.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de ViewTestDrive.fxml");
        }
    }
}
