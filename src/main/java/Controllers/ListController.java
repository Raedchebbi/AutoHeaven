package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import models.avis;
import service.AvisService;
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
    public void initialize() throws Exception {
        loadAvis();
        setupSearch();

        // Personnalisation des cellules du ListView
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
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des avis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupSearch() {
        searchButton.setOnAction(event -> rechercherAvis());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> rechercherAvis());
    }

    private void rechercherAvis() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            listViewAvis.setItems(avisObservableList);
            return;
        }

        ObservableList<avis> filteredList = FXCollections.observableArrayList();
        for (avis avis : avisObservableList) {
            if (avis.getCommentaire().toLowerCase().contains(searchText) ||
                    String.valueOf(avis.getNote()).contains(searchText)) {
                filteredList.add(avis);
            }
        }

        listViewAvis.setItems(filteredList);
    }
}
