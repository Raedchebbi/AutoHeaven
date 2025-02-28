package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CamionRemorquage;
import models.ResRemorquage;
import models.User;
import services.ResRemorquageService;
import services.UserService;
import services.CamionRemorquageService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class UpdateRemorquageController {

    @FXML
    private ComboBox<String> cbUser;
    @FXML
    private ComboBox<String> cbCamion;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Text errorMessage;
    @FXML
    private Text successMessage;
    @FXML
    private Button btnBack;

    private ResRemorquageService remorquageService = new ResRemorquageService();
    private UserService userService = new UserService();
    private CamionRemorquageService camionService = new CamionRemorquageService();
    private ResRemorquage remorquage;

    @FXML
    public void initialize() {
        loadUsers();
        loadCamions();
        successMessage.setText("");
        errorMessage.setText("");
    }

    public void setRemorquage(ResRemorquage remorquage) throws Exception {
        this.remorquage = remorquage;
        populateFields();
    }

    private void populateFields() throws Exception {
        cbUser.setValue(remorquage.getId_u() + " - " + userService.getById(remorquage.getId_u()).getUsername());
        cbCamion.setValue(remorquage.getId_cr() + " - " + camionService.getById(remorquage.getId_cr()).getNomAgence());
        dpDate.setValue(remorquage.getDate().toLocalDate());
    }

    private void loadUsers() {
        try {
            List<User> users = userService.getAll();
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : users) {
                userNames.add(user.getUsername() + " (ID : " + user.getId() + ")");
            }
            cbUser.setItems(userNames);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading users : " + e.getMessage());
        }
    }

    private void loadCamions() {
        try {
            List<CamionRemorquage> camions = camionService.getAll();
            ObservableList<String> nomAgenceList = FXCollections.observableArrayList();
            for (CamionRemorquage camion : camions) {
                nomAgenceList.add(camion.getNomAgence() + " : " + camion.getStatut());
            }
            cbCamion.setItems(nomAgenceList);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading nom_agence values: " + e.getMessage());
        }
    }

    @FXML
    public void updateRemorquage() {
        errorMessage.setText("");
        successMessage.setText("");

        try {
            // Vérification des champs obligatoires
            if (cbUser.getValue() == null) {
                errorMessage.setText("Utilisateur est requis !");
                return;
            }
            if (cbCamion.getValue() == null) {
                errorMessage.setText("Camion est requis !");
                return;
            }
            if (dpDate.getValue() == null) {
                errorMessage.setText("Veuillez sélectionner une date !");
                return;
            }

            LocalDate date = dpDate.getValue();
            if (!date.isAfter(LocalDate.now())) {
                errorMessage.setText("La date doit être supérieure à aujourd'hui !");
                return;
            }

            int userId = Integer.parseInt(cbUser.getValue().split(" - ")[0]);
            int camionId = Integer.parseInt(cbCamion.getValue().split(" - ")[0]);

            remorquage.setId_u(userId);
            remorquage.setId_cr(camionId);
            remorquage.setDate(java.sql.Date.valueOf(date));
            remorquageService.update(remorquage);
            successMessage.setText("Remorquage mis à jour avec succès !");

            // Retour à la vue
            goToViewRemorquage("Remorquage mis à jour avec succès !");
        } catch (Exception e) {
            errorMessage.setText("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    private void goToViewRemorquage(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewRemorquage.fxml"));
            Parent root = loader.load();

            // Passer le message de succès à la vue
            ViewRemorquageController viewController = loader.getController();
            viewController.displaySuccessMessage(message); // Méthode à créer dans ViewRemorquageController

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewRemorquage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
        cbCamion.setValue(null);
        dpDate.setValue(null);
    }
}