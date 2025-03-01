package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.ResMecanicien;
import models.User;
import services.ResMecanicienService;
import services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMecanicienRDVController {

    @FXML
    private TextField search_id;

    @FXML
    private VBox mecanicien_container;

    @FXML
    private Button add_btn;

    @FXML
    private Button back_btn;

    @FXML
    private Label successMessage;

    private ResMecanicienService mecanicienService = new ResMecanicienService();
    private UserService userService = new UserService();
    private List<ResMecanicien> mecanicienList; // Liste originale pour filtrage

    @FXML
    public void initialize() {
        loadMecanicienRDVs();
        successMessage.setVisible(false);
        setupSearch(); // Configurer la recherche
    }

    private void loadMecanicienRDVs() {
        try {
            mecanicienList = mecanicienService.getAll(); // Stocke la liste originale
            populateMecanicienContainer(mecanicienList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMecanicienContainer(List<ResMecanicien> mecanicienList) throws Exception {
        mecanicien_container.getChildren().clear();
        for (ResMecanicien rdv : mecanicienList) {
            HBox hbox = new HBox();
            hbox.setSpacing(20);

            Label adresseLabel = new Label(rdv.getAdresse());
            adresseLabel.setPrefWidth(150.0);

            Label noteLabel = new Label(rdv.getNote());
            noteLabel.setPrefWidth(150.0);

            User user = userService.getById(rdv.getId_u());
            String userName = (user != null) ? user.getUsername() : "Unknown";
            Label userLabel = new Label(userName + " (ID: " + rdv.getId_u() + ")");
            userLabel.setPrefWidth(120.0);

            User mecanicien = userService.getById(rdv.getId_mec());
            String mecanicienName = (mecanicien != null) ? mecanicien.getUsername() : "Unknown";
            Label mecanicienLabel = new Label(mecanicienName + " (ID: " + rdv.getId_mec() + ")");
            mecanicienLabel.setPrefWidth(120.0);

            Label dateLabel = new Label(rdv.getDate().toString());
            dateLabel.setPrefWidth(120.0);

            Label statusLabel = new Label(rdv.getStatus());
            statusLabel.setPrefWidth(120.0);

            // Action buttons
            if (rdv.getStatus().equals("en_cours_de_traitement")) {
                Button confirmButton = new Button("Confirmer");
                confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                confirmButton.setOnAction(e -> {
                    rdv.setStatus("confirmee");
                    try {
                        mecanicienService.update(rdv);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    successMessage.setText("Rendez-vous confirmé !");
                    successMessage.setVisible(true);
                    loadMecanicienRDVs();
                });

                Button rejectButton = new Button("Rejeter");
                rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                rejectButton.setOnAction(e -> {
                    rdv.setStatus("rejetee");
                    try {
                        mecanicienService.update(rdv);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    successMessage.setText("Rendez-vous rejeté !");
                    successMessage.setVisible(true);
                    loadMecanicienRDVs();
                });

                hbox.getChildren().addAll(adresseLabel, noteLabel, userLabel, mecanicienLabel, dateLabel, statusLabel, confirmButton, rejectButton);
            } else if (rdv.getStatus().equals("confirmee") || rdv.getStatus().equals("rejetee")) {
                Button updateButton = new Button("Modifier");
                updateButton.setOnAction(e -> openUpdateInterface(rdv));

                Button deleteButton = new Button("Supprimer");
                deleteButton.setOnAction(e -> confirmDeleteRendezVous(rdv));

                hbox.getChildren().addAll(adresseLabel, noteLabel, userLabel, mecanicienLabel, dateLabel, statusLabel, updateButton, deleteButton);
            }

            mecanicien_container.getChildren().add(hbox);
        }
    }

    private void setupSearch() {
        search_id.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterMecanicienRDVs(newValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void filterMecanicienRDVs(String query) throws Exception {
        List<ResMecanicien> filteredList = mecanicienList.stream()
                .filter(rdv -> {
                    try {
                        return rdv.getAdresse().toLowerCase().contains(query.toLowerCase()) ||
                                rdv.getNote().toLowerCase().contains(query.toLowerCase()) ||
                                userService.getById(rdv.getId_u()).getUsername().toLowerCase().contains(query.toLowerCase()) ||
                                userService.getById(rdv.getId_mec()).getUsername().toLowerCase().contains(query.toLowerCase()) ||
                                rdv.getDate().toString().contains(query) ||
                                rdv.getStatus().toLowerCase().contains(query.toLowerCase());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        populateMecanicienContainer(filteredList);
    }

    private void confirmDeleteRendezVous(ResMecanicien rdv) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de Suppression");
        alert.setHeaderText("Vous êtes sur le point de supprimer un rendez-vous.");
        alert.setContentText("Êtes-vous sûr de vouloir continuer ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteRendezVous(rdv);
            }
        });
    }

    private void deleteRendezVous(ResMecanicien rdv) {
        try {
            mecanicienService.delete(rdv.getId_res_m());
            successMessage.setText("Rendez-vous supprimé avec succès !");
            successMessage.setVisible(true);
            loadMecanicienRDVs();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openUpdateInterface(ResMecanicien rdv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateMecanicienRDV.fxml"));
            Parent root = loader.load();

            UpdateMecanicienController updateController = loader.getController();
            updateController.setResMecanicien(rdv); // Pass the selected ResMecanicien

            Stage stage = (Stage) add_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Navigator.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAddMecanicienRDV() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMecanicienRDV.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) add_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}