package controllers;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard1 implements Initializable {
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
    private AnchorPane equipement_form;
    @FXML
    private AnchorPane comform;




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
    private Button voitureBtn;
    @FXML
    private Button combtn ;
    @FXML
    private AnchorPane voitures_form;
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
    private ListEquipement listEquipementController;
    @FXML
    private ValidationCommande commandeController;

     @FXML
    private Button equipbtn;



    private Image image;
    public void close(){
        System.exit(0);
    }
    public void switchForm(ActionEvent event){
        if(event.getSource() == acceuilBtn){
            Acceuil_form.setVisible(true);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            voitures_form.setVisible(false); // Add this line

            acceuilBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            voitureBtn.setStyle("-fx-background-color: transparent"); // Add this line

        } else if (event.getSource() == addMecanicienBtn){
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(true);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            voitures_form.setVisible(false); // Add this line

            addMecanicienBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            voitureBtn.setStyle("-fx-background-color: transparent"); // Add this line

        } else if (event.getSource() == clientsBtn){
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(true);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            voitures_form.setVisible(false); // Add this line

            clientsBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            voitureBtn.setStyle("-fx-background-color: transparent"); // Add this line

        } else if (event.getSource() == OffreBtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(true);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            voitures_form.setVisible(false); // Add this line

            OffreBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            clientsBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            voitureBtn.setStyle("-fx-background-color: transparent"); // Add this line

        } else if (event.getSource() == equipbtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(true);
            comform.setVisible(false);
            voitures_form.setVisible(false); // Add this line

            equipbtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            clientsBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
            voitureBtn.setStyle("-fx-background-color: transparent"); // Add this line

        } else if (event.getSource() == combtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(true);
            voitures_form.setVisible(false); // Add this line

            equipbtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            voitureBtn.setStyle("-fx-background-color: transparent"); // Add this line

        } else if (event.getSource() == voitureBtn) {
            Acceuil_form.setVisible(false);
            Mecaniciens_form.setVisible(false);
            clients_form.setVisible(false);
            Offre_form.setVisible(false);
            equipement_form.setVisible(false);
            comform.setVisible(false);
            voitures_form.setVisible(true);  // Show the voitures form

            voitureBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #fa4040, #766f6f)");
            acceuilBtn.setStyle("-fx-background-color: transparent");
            addMecanicienBtn.setStyle("-fx-background-color: transparent");
            clientsBtn.setStyle("-fx-background-color: transparent");
            OffreBtn.setStyle("-fx-background-color: transparent");
            equipbtn.setStyle("-fx-background-color: transparent");
            combtn.setStyle("-fx-background-color: transparent");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
            Parent listEquipement = loader.load();
            listEquipementController = loader.getController();
            equipement_form.getChildren().add(listEquipement);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/navigate.fxml"));
            Parent navigate = loader.load();
            // If you want to add it to a container (e.g., a pane), you can do so here:
            // someContainer.getChildren().add(navigate);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
