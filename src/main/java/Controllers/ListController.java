package Controllers;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import models.avis;
import service.AvisService;

import java.util.List;

public class ListController {
    private static final int PAGE_SIZE = 10;  // Nombre d'avis par page
    private int currentPage = 0;  // Page actuelle



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
        loadAvis();  // Charger les avis initiaux
        setupSearch();


        // Personnalisation des cellules du ListView avec des informations visuellement plus attrayantes
        listViewAvis.setCellFactory(param -> new ListCell<avis>() {
            @Override
            protected void updateItem(avis avis, boolean empty) {
                super.updateItem(avis, empty);
                if (empty || avis == null) {
                    setText(null);
                    setStyle("");
                } else {
                    // Utilisation d'un design plus propre avec des données bien formatées
                    setText("Note: " + avis.getNote() + "\nCommentaire: " + avis.getCommentaire() + "\nDate: " + avis.getDateavis());
                    setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #ddd;");

                    // Animation de fade-in pour chaque élément
                    fadeIn(this);
                }
            }
        });
        listViewAvis.setOnScroll(event -> {
            // Vérifier si l'utilisateur est à la fin de la liste
            if (listViewAvis.getItems().size() == PAGE_SIZE &&
                    listViewAvis.getLayoutBounds().getMaxY() == listViewAvis.getHeight()) {
                currentPage++;  // Passer à la page suivante
                loadAvis();  // Charger les avis de la page suivante
            }
        });

    }

    private void loadAvis() {
        try {
            List<avis> avisList = avisService.getAvisByPage(currentPage, PAGE_SIZE);  // Charger les avis pour la page actuelle
            avisObservableList.setAll(avisList);
            listViewAvis.setItems(avisObservableList);
             // Mettre à jour l'état des boutons de pagination
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des avis: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadAvis();
        }
    }

    @FXML
    private void nextPage() {
        currentPage++;
        loadAvis();
    }

    private void fadeIn(ListCell<avis> cell) {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(cell);
        fadeIn.setDuration(Duration.seconds(0.5));
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
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

    // Méthode pour configurer la pagination (initialisation des boutons)

}
