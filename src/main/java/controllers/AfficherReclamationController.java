package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AfficherReclamationController {

    @FXML
    private VBox vbox;

    @FXML
    private HBox titleRow;

    // Méthode d'initialisation
    @FXML
    public void initialize() {
        // Assurez-vous que les éléments sont bien initialisés
        if (vbox == null || titleRow == null) {
            System.out.println("Les éléments FXML ne sont pas correctement injectés !");
        }
    }

    // Autres méthodes pour manipuler la logique de votre application
}
