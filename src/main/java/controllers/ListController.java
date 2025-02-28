//package Controllers;
//
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.TextField;
//import models.avis;
//import service.AvisService;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import java.sql.SQLException;
//import java.util.List;
//
//public class ListController {
//    @FXML
//    private ListView<avis> listViewAvis;
//    @FXML
//    private TextField searchField;
//    @FXML
//    private Button searchButton;
//    private AvisService avisService;
//    private ObservableList<avis> avisObservableList;
//
//    public ListController() {
//        avisService = new AvisService();
//        avisObservableList = FXCollections.observableArrayList();
//    }
//    private ObservableList<String> avisList = FXCollections.observableArrayList();
//
//    @FXML
//    public void initialize() {
//        loadAvis();
//
//        listViewAvis.setCellFactory(param -> new ListCell<avis>() {
//            @Override
//            protected void updateItem(avis avis, boolean empty) {
//                super.updateItem(avis, empty);
//                if (empty || avis == null) {
//                    setText(null);
//                } else {
//                    setText("Note: " + avis.getNote() + "\nCommentaire: " + avis.getCommentaire() + "\nDate: " + avis.getDateavis());
//                }
//            }
//        });
//    }
//
//    private void loadAvis() {
//        try {
//            List<avis> avisList = avisService.getAll();
//            avisObservableList.setAll(avisList);
//            listViewAvis.setItems(avisObservableList);
//            // Activer la recherche
//            searchButton.setOnAction(event -> rechercherAvis());
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> rechercherAvis());
//        } catch (SQLException e) {
//            System.out.println("Erreur lors du chargement des avis: " + e.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private void rechercherAvis() {
//        String searchText = searchField.getText().toLowerCase();
//        if (searchText.isEmpty()) {
//            listViewAvis.setItems(avisList);
//            return;
//        }
//
//        ObservableList<String> filteredList = FXCollections.observableArrayList();
//        for (String avis : avisList) {
//            if (avis.toLowerCase().contains(searchText)) {
//                filteredList.add(avis);
//            }
//        }
//
//        listViewAvis.setItems(filteredList);
//    }
//}


package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import models.avis;
import services.AvisService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.List;

public class ListController {
    @FXML
    private ListView<avis> listViewAvis;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    private AvisService avisService;
    private ObservableList<avis> avisObservableList;

    public ListController() {
        avisService = new AvisService();
        avisObservableList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        loadAvis();

        listViewAvis.setCellFactory(param -> new ListCell<avis>() {
            @Override
            protected void updateItem(avis avis, boolean empty) {
                super.updateItem(avis, empty);
                if (empty || avis == null) {
                    setText(null);
                } else {
                    setText("Note: " + avis.getNote() + "\nCommentaire: " + avis.getCommentaire() + "\nDate: " + avis.getDateavis());
                }
            }
        });
    }

    private void loadAvis() {
        try {
            List<avis> avisList = avisService.getAll();
            avisObservableList.setAll(avisList);
            listViewAvis.setItems(avisObservableList);

            // Enable search functionality
            searchButton.setOnAction(event -> rechercherAvis());
            searchField.textProperty().addListener((observable, oldValue, newValue) -> rechercherAvis());
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des avis: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void rechercherAvis() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            listViewAvis.setItems(avisObservableList);
            return;
        }

        ObservableList<avis> filteredList = FXCollections.observableArrayList();
        for (avis avis : avisObservableList) {
            if (avis.getCommentaire().toLowerCase().contains(searchText) || String.valueOf(avis.getNote()).contains(searchText)) {
                filteredList.add(avis);
            }
        }

        listViewAvis.setItems(filteredList);
    }
}
