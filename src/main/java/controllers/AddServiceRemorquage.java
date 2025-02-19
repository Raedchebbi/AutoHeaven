package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import models.ServiceRemorquage;
import services.ServiceRemorquageService;

import java.util.Date;

public class AddServiceRemorquage {

    @FXML
    private TextField idVoitureField;
    @FXML
    private TextField nomChauffeurField;
    @FXML
    private TextField lieuField;
    @FXML
    private DatePicker dateServicePicker;
    @FXML
    private TextField statutField;
    @FXML
    private Button addButton;

    private ServiceRemorquageService serviceRemorquageService;

    @FXML
    public void initialize() {
        serviceRemorquageService = new ServiceRemorquageService();
    }

    @FXML
    private void handleAddService() {
        try {
            int idVr = Integer.parseInt(idVoitureField.getText());
            String nomChauffeur = nomChauffeurField.getText();
            String lieu = lieuField.getText();
            Date dateService = java.sql.Date.valueOf(dateServicePicker.getValue());
            String statut = statutField.getText();

            ServiceRemorquage service = new ServiceRemorquage(0, idVr, nomChauffeur, lieu, dateService, statut);
            serviceRemorquageService.create(service);
            
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        idVoitureField.clear();
        nomChauffeurField.clear();
        lieuField.clear();
        dateServicePicker.setValue(null);
        statutField.clear();
    }
}