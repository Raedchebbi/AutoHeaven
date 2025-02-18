package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.MyDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AfficherReclamationController {

    @FXML private VBox contentBox;
    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        String query = "SELECT r.id_rec, r.titre, r.contenu, "
                + "COALESCE(r.status, 'en_attente') as status, "
                + "r.datecreation, u.nom, u.tel, u.email "
                + "FROM reclamation r "
                + "JOIN user u ON r.id = u.id "
                + "ORDER BY r.datecreation DESC";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            contentBox.getChildren().clear();

            while (rs.next()) {
                addDataRow(
                        rs.getInt("id_rec"),
                        rs.getString("titre"),
                        rs.getString("contenu"),
                        rs.getString("status"),
                        rs.getTimestamp("datecreation").toLocalDateTime().format(dateFormatter),
                        rs.getString("nom"),
                        rs.getString("tel"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            showError("Erreur SQL", "Erreur de chargement :\n" + e.getMessage());
        }
    }

    private void addDataRow(int idRec, String titre, String contenu, String status,
                            String date, String nom, String tel, String email) {

        HBox row = new HBox(10);
        row.getStyleClass().add("data-row");

        // Création des composants
        Label lblTitre = createCell(titre, 200);
        Label lblContenu = createCell(contenu, 300);
        Label lblDate = createCell(date, 150);
        Label lblNom = createCell(nom, 150);
        Label lblTel = createCell(tel, 100);
        Label lblEmail = createCell(email, 200);

        // Bouton de statut
        Button btnStatus = new Button();
        btnStatus.setPrefWidth(120);
        updateButtonState(btnStatus, status, idRec, titre, contenu);

        row.getChildren().addAll(lblTitre, lblContenu, lblDate, lblNom, lblTel, lblEmail, btnStatus);
        contentBox.getChildren().add(row);
    }

    private void updateButtonState(Button btn, String status, int idRec, String titre, String contenu) {
        if ("traité".equalsIgnoreCase(status)) {
            btn.setText("Traité");
            btn.setDisable(true);
            btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        } else {
            btn.setText("En attente");
            btn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            btn.setOnAction(e -> openRepondreForm(idRec, titre, contenu));
        }
    }

    private void openRepondreForm(int idRec, String titre, String contenu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/repondrereclamation.fxml"));
            Parent root = loader.load();

            repondre_rec_controller controller = loader.getController();
            controller.setReclamationId(idRec);
            controller.setReclamationTitre(titre);
            controller.setReclamationContent(contenu);

            controller.setOnSuccessCallback(this::refreshData);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Répondre à la réclamation - " + titre);
            stage.show();

        } catch (IOException e) {
            showError("Erreur", "Erreur de chargement du formulaire");
        }
    }

    public void refreshData() {
        loadDataFromDatabase();
    }

    private Label createCell(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setWrapText(true);
        label.setStyle("-fx-padding: 5; -fx-border-color: #ddd;");
        return label;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}