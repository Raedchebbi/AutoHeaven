package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CamionRemorquage;
import models.ResRemorquage;
import models.User;
import services.CamionRemorquageService;
import services.ResRemorquageService;
import services.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddRemorquageController {

    @FXML
    private ComboBox<String> cbUser;
    @FXML
    private ComboBox<String> cbNomAgence;
    @FXML
    private TextField tfPointRamassage;
    @FXML
    private TextField tfPointDepot;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private Text errorMessage;
    @FXML
    private Text successMessage;

    private ResRemorquageService resRemorquageService = new ResRemorquageService();
    private CamionRemorquageService camionRemorquageService = new CamionRemorquageService();
    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        populateUserComboBox();
        populateNomAgenceComboBox();
        successMessage.setText("");
        errorMessage.setText("");
    }

    private void populateUserComboBox() {
        try {
            List<User> users = userService.getAll();
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : users) {
                userNames.add(user.getNom() + " " + user.getPrenom() + " (ID : " + user.getId() + ")");
            }
            cbUser.setItems(userNames);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading users : " + e.getMessage());
        }
    }

    private void populateNomAgenceComboBox() {
        try {
            List<CamionRemorquage> camions = camionRemorquageService.getAll();
            ObservableList<String> nomAgenceList = FXCollections.observableArrayList();
            for (CamionRemorquage camion : camions) {
                nomAgenceList.add(camion.getNomAgence() + " : " + camion.getStatut());
            }
            cbNomAgence.setItems(nomAgenceList);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading nom_agence values: " + e.getMessage());
        }
    }

    @FXML
    private void addRemorquage() throws Exception {
        errorMessage.setText("");
        successMessage.setText("");

        String selectedUser = cbUser.getValue();
        if (selectedUser == null) {
            errorMessage.setText("User est requis !");
            return;
        }
        int userId = extractIdFromComboBox(selectedUser);

        String nomAgence = cbNomAgence.getValue();
        if (nomAgence == null) {
            errorMessage.setText("Nom de l'agence est requis !");
            return;
        }

        CamionRemorquage selectedCamion = findCamionByNomAgence(nomAgence);
        if (selectedCamion == null || !selectedCamion.getStatut().equals("Disponible")) {
            errorMessage.setText("Le camion doit être 'Disponible' !");
            return;
        }

        String pointRamassage = tfPointRamassage.getText();
        if (pointRamassage.isEmpty()) {
            errorMessage.setText("Point Ramassage est requis !");
            return;
        }

        String pointDepot = tfPointDepot.getText();
        if (pointDepot.isEmpty()) {
            errorMessage.setText("Point Depot est requis !");
            return;
        }

        LocalDate date = dpDate.getValue();
        if (date == null) {
            errorMessage.setText("Veuillez sélectionner une date de format : JJ/MM/AAAA.");
            return;
        }
        if (!date.isAfter(LocalDate.now())) {
            errorMessage.setText("La date doit être supérieure à aujourd'hui !");
            return;
        }

        int idCr = selectedCamion.getId_cr();
        ResRemorquage resRemorquage = new ResRemorquage();
        resRemorquage.setId_u(userId);
        resRemorquage.setId_cr(idCr);
        resRemorquage.setPoint_ramassage(pointRamassage);
        resRemorquage.setPoint_depot(pointDepot);
        resRemorquage.setDate(java.sql.Date.valueOf(date));
        resRemorquage.setStatus("en_cours_de_traitement");

        try {
            resRemorquageService.create(resRemorquage);
            successMessage.setText("Remorquage ajouté avec succès !");
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    private CamionRemorquage findCamionByNomAgence(String nomAgence) throws Exception {
        List<CamionRemorquage> camions = camionRemorquageService.getAll();
        for (CamionRemorquage camion : camions) {
            if (camion.getNomAgence().equals(nomAgence.split(" : ")[0])) {
                return camion;
            }
        }
        return null;
    }

    private int extractIdFromComboBox(String selectedText) {
        String idStr = selectedText.replaceAll(".*\\(ID : (\\d+)\\)", "$1");
        return Integer.parseInt(idStr);
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewRemorquage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading navigator interface.");
        }
    }

    @FXML
    private void cancelAction() {
        clearFields();
        errorMessage.setText("");
        successMessage.setText("");
    }

    private void clearFields() {
        cbUser.setValue(null);
        cbNomAgence.setValue(null);
        tfPointRamassage.clear();
        tfPointDepot.clear();
        dpDate.setValue(null);
    }
}