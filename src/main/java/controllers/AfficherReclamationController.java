package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.MyDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AfficherReclamationController {
    @FXML private AnchorPane repondreFormContainer;
    @FXML private repondre_rec_controller repondreFormController;

    @FXML private VBox contentBox;
    @FXML private CheckBox filterBannedCheckBox;
    @FXML private TextField searchField;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private dashboardController dashboardController;

    public void setDashboardController(dashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    public void initialize() {
        loadDataFromDatabase(false, "");
        filterBannedCheckBox.setOnAction(e -> loadDataFromDatabase(filterBannedCheckBox.isSelected(), searchField.getText()));
        searchField.textProperty().addListener((observable, oldValue, newValue) -> loadDataFromDatabase(filterBannedCheckBox.isSelected(), newValue));
        repondreFormContainer.setVisible(false);

        if (repondreFormController == null) {
            System.err.println("repondreFormController is not injected. Check FXML and controller setup.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/repondrereclamation.fxml"));
                Parent form = loader.load();
                repondreFormController = loader.getController();
                repondreFormContainer.getChildren().setAll(form);
            } catch (IOException e) {
                showError("Erreur", "Erreur de chargement du formulaire : " + e.getMessage());
            }
        }
    }

    private void loadDataFromDatabase(boolean showOnlyBanned, String searchQuery) {
        String query = "SELECT r.id_rec, r.titre, r.contenu, " +
                "COALESCE(r.status, 'en_attente') as status, " +
                "r.datecreation, u.nom, u.tel, u.email " +
                "FROM reclamation r " +
                "JOIN user u ON r.id = u.id " +
                "ORDER BY r.datecreation DESC";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            contentBox.getChildren().clear();
            List<HBox> rows = processResultSet(rs, showOnlyBanned, searchQuery);
            contentBox.getChildren().addAll(rows);

        } catch (SQLException e) {
            showError("Erreur SQL", "Erreur de chargement :\n" + e.getMessage());
        }
    }

    private List<HBox> processResultSet(ResultSet rs, boolean showOnlyBanned, String searchQuery) throws SQLException {
        List<HBox> rows = new ArrayList<>();
        List<Reclamation> reclamations = new ArrayList<>();

        while (rs.next()) {
            int id_rec = rs.getInt("id_rec");
            String titre = rs.getString("titre");
            String contenu = rs.getString("contenu");
            String status = rs.getString("status");
            Timestamp timestamp = rs.getTimestamp("datecreation");
            String nom = rs.getString("nom");
            String tel = rs.getString("tel");
            String email = rs.getString("email");

            Reclamation reclamation = new Reclamation(id_rec, titre, contenu, status, timestamp, nom, tel, email);
            reclamations.add(reclamation);
        }

        String searchLower = searchQuery != null ? searchQuery.toLowerCase() : "";
        if (showOnlyBanned) {
            return reclamations.stream()
                    .filter(r -> containsBadWord(r.getContenu()))
                    .filter(r -> searchLower.isEmpty() || r.getTitre().toLowerCase().contains(searchLower))
                    .map(r -> createDataRow(r.getIdRec(), r.getTitre(), r.getContenu(), r.getStatus(), r.getTimestamp(), r.getNom(), r.getTel(), r.getEmail(), containsBadWord(r.getContenu())))
                    .collect(Collectors.toList());
        } else {
            return reclamations.stream()
                    .filter(r -> searchLower.isEmpty() || r.getTitre().toLowerCase().contains(searchLower))
                    .map(r -> createDataRow(r.getIdRec(), r.getTitre(), r.getContenu(), r.getStatus(), r.getTimestamp(), r.getNom(), r.getTel(), r.getEmail(), containsBadWord(r.getContenu())))
                    .collect(Collectors.toList());
        }
    }

    private HBox createDataRow(int id_rec, String titre, String contenu, String status, Timestamp timestamp, String nom, String tel, String email, boolean isBannedContent) {
        HBox row = new HBox(15);
        row.setPrefWidth(980.0);
        row.getStyleClass().add("data-row");

        Label lblTitre = createCell(titre, 160.0);
        Label lblContenu = createCell(isBannedContent ? "BANNED 🚩" : contenu, 260.0);
        if (isBannedContent) {
            lblContenu.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
        Label lblDate = createCell(timestamp.toLocalDateTime().format(dateFormatter), 140.0);
        Label lblNom = createCell(nom, 140.0);
        Label lblTel = createCell(tel, 120.0);
        Label lblEmail = createCell(email, 240.0);
        lblEmail.setWrapText(true);

        Button btnStatus = new Button(status);
        btnStatus.setPrefWidth(140.0);
        btnStatus.setWrapText(true);
        updateButtonState(btnStatus, id_rec, titre, contenu, status, isBannedContent);

        // Icône de traduction (langue.png)
        ImageView translateIcon = new ImageView(getClass().getResource("/images/langue.png").toExternalForm());
        translateIcon.setFitWidth(25.0);
        translateIcon.setFitHeight(25.0);
        translateIcon.setPreserveRatio(true);
        setupTranslationMenu(translateIcon, lblContenu, contenu);

        // Icône de poubelle (poubelle.png) à côté de l’icône de langue
        ImageView deleteIcon = new ImageView(getClass().getResource("/images/test.png").toExternalForm());
        deleteIcon.setFitWidth(25.0);
        deleteIcon.setFitHeight(25.0);
        deleteIcon.setPreserveRatio(true);
        deleteIcon.setOnMouseClicked(e -> deleteReclamation(id_rec)); // Action de suppression au clic

        // Placer les icônes de langue et de poubelle côte à côte à la fin
        HBox iconsBox = new HBox(5, translateIcon, deleteIcon);

        row.getChildren().addAll(lblTitre, lblContenu, lblDate, lblNom, lblTel, lblEmail, btnStatus, iconsBox);
        return row;
    }

    private void deleteReclamation(int idRec) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Voulez-vous vraiment supprimer cette réclamation ? Cette action est irréversible.");
        confirmation.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        confirmation.getDialogPane().setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-padding: 10px;");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String deleteMessagesQuery = "DELETE FROM messagerie WHERE id_rec = ?";
            String deleteReclamationQuery = "DELETE FROM reclamation WHERE id_rec = ?";

            try (Connection conn = MyDb.getInstance().getConn();
                 PreparedStatement psMessages = conn.prepareStatement(deleteMessagesQuery);
                 PreparedStatement psReclamation = conn.prepareStatement(deleteReclamationQuery)) {

                // Supprimer les messages associés
                psMessages.setInt(1, idRec);
                int messagesDeleted = psMessages.executeUpdate();
                System.out.println("Messages supprimés : " + messagesDeleted);

                // Supprimer la réclamation
                psReclamation.setInt(1, idRec);
                int reclamationDeleted = psReclamation.executeUpdate();

                if (reclamationDeleted > 0) {
                    showAlert("Succès", "Réclamation et messages associés supprimés avec succès.");
                    loadDataFromDatabase(filterBannedCheckBox.isSelected(), searchField.getText()); // Rafraîchir la liste
                } else {
                    showError("Erreur", "Aucune réclamation trouvée avec cet ID ou échec de la suppression.");
                }

            } catch (SQLException e) {
                showError("Erreur SQL", "Erreur lors de la suppression de la réclamation : " + e.getMessage());
            }
        }
    }

    private void updateButtonState(Button btn, int id_rec, String titre, String contenu, String status, boolean isBannedContent) {
        if (isBannedContent) {
            btn.setText("Bloqué");
            btn.setDisable(true);
            btn.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
        } else if ("repondu".equalsIgnoreCase(status)) {
            btn.setStyle("-fx-background-color: linear-gradient(to right, #A5D6A7, #8BC34A); -fx-text-fill: #2E7D32; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-background-radius: 8px; -fx-effect: dropshadow(one-pass-box, rgba(0, 0, 0, 0.1), 2, 0, 1, 1);");
            btn.setDisable(true);
        } else if ("en_attente".equalsIgnoreCase(status)) {
            btn.setStyle("-fx-background-color: linear-gradient(to right, #FFCDD2, #FF8A80); -fx-text-fill: #C62828; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-background-radius: 8px; -fx-effect: dropshadow(one-pass-box, rgba(0, 0, 0, 0.1), 2, 0, 1, 1);");
            btn.setOnAction(e -> openRepondreFormInline(id_rec, titre, contenu, status));
        }
    }

    private void setupTranslationMenu(ImageView translateIcon, Label contenuLabel, String originalContent) {
        ContextMenu contextMenu = new ContextMenu();
        String[] languages = {"Anglais", "Français", "Espagnol", "Allemand", "Italien"};

        for (String lang : languages) {
            MenuItem item = new MenuItem(lang);
            item.setOnAction(e -> translateContent(originalContent, lang.toLowerCase(), contenuLabel));
            contextMenu.getItems().add(item);
        }

        translateIcon.setOnMouseClicked(e -> contextMenu.show(translateIcon, javafx.geometry.Side.BOTTOM, 0, 0));
    }

    private void translateContent(String content, String targetLanguage, Label contenuLabel) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyBovR0DquNLAyKmSjnYxN6BD1FgxckdHPU";
        String prompt = "Traduisez ce texte en " + targetLanguage + " : " + content;

        java.util.Map<String, Object> messagePart = new java.util.HashMap<>();
        messagePart.put("text", prompt);

        java.util.Map<String, Object> contentPart = new java.util.HashMap<>();
        contentPart.put("parts", new Object[]{messagePart});

        java.util.Map<String, Object> requestBody = new java.util.HashMap<>();
        requestBody.put("contents", new Object[]{contentPart});

        try {
            String jsonPayload = objectMapper.writeValueAsString(requestBody);
            RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JsonNode rootNode = objectMapper.readTree(responseBody);
                    String translatedText = rootNode.path("candidates")
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();
                    contenuLabel.setText(translatedText);
                } else {
                    showError("Erreur", "Échec de la traduction : " + response.code());
                }
            }
        } catch (IOException e) {
            showError("Erreur", "Erreur technique lors de la traduction : " + e.getMessage());
        }
    }

    private void openRepondreFormInline(int reclamationId, String titre, String contenu, String status) {
        if (repondreFormController == null) {
            System.err.println("repondreFormController is null. Check FXML loading.");
            return;
        }

        repondreFormController.setReclamationId(reclamationId);
        repondreFormController.setReclamationTitre(titre);
        repondreFormController.setReclamationContent(contenu);
        repondreFormController.setOnSuccessCallback(() -> {
            loadDataFromDatabase(filterBannedCheckBox.isSelected(), searchField.getText());
            repondreFormContainer.setVisible(false);
        });

        repondreFormContainer.setVisible(true);
        repondreFormContainer.setManaged(true);
    }

    private Label createCell(String text, double width) {
        Label label = new Label(text != null ? text : "");
        label.setPrefWidth(width);
        label.setWrapText(true);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().add("data-cell");
        return label;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        alert.getDialogPane().setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-padding: 10px;");
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        alert.getDialogPane().setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-padding: 10px;");
        alert.showAndWait();
    }

    private boolean containsBadWord(String text) {
        String lowerText = text.toLowerCase();
        String[] badWords = {"badword", "insulte", "profanité"};
        for (String badWord : badWords) {
            if (lowerText.contains(badWord)) {
                return true;
            }
        }
        return false;
    }

    private static class Reclamation {
        private final int idRec;
        private final String titre;
        private final String contenu;
        private final String status;
        private final Timestamp timestamp;
        private final String nom;
        private final String tel;
        private final String email;

        public Reclamation(int idRec, String titre, String contenu, String status, Timestamp timestamp, String nom, String tel, String email) {
            this.idRec = idRec;
            this.titre = titre != null ? titre : "";
            this.contenu = contenu != null ? contenu : "";
            this.status = status != null ? status : "en_attente";
            this.timestamp = timestamp;
            this.nom = nom != null ? nom : "";
            this.tel = tel != null ? tel : "";
            this.email = email != null ? email : "";
        }

        public int getIdRec() { return idRec; }
        public String getTitre() { return titre; }
        public String getContenu() { return contenu; }
        public String getStatus() { return status; }
        public Timestamp getTimestamp() { return timestamp; }
        public String getNom() { return nom; }
        public String getTel() { return tel; }
        public String getEmail() { return email; }
    }
}