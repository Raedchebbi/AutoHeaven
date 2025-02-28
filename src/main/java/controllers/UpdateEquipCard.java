package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.Equipement;
import models.EquipementAffichage;
import services.EquipementService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class UpdateEquipCard {
    @FXML
    private Button add_image;

    @FXML
    private TextArea desc;

    @FXML
    private Text error_desc;

    @FXML
    private Text error_image;

    @FXML
    private Text error_marque;

    @FXML
    private Text error_nom;

    @FXML
    private Text error_prix;

    @FXML
    private Text error_quantite;

    @FXML
    private Text error_ref;

    @FXML
    private HBox h_error_desc;

    @FXML
    private HBox h_error_image;

    @FXML
    private HBox h_error_marque;

    @FXML
    private HBox h_error_nom;

    @FXML
    private HBox h_error_prix;

    @FXML
    private HBox h_error_quantite;

    @FXML
    private HBox h_error_ref;

    @FXML
    private ImageView imageInput;
    private String imageName;
    @FXML
    private File selectedImageFile;

    @FXML
    private TextField marque;

    @FXML
    private TextField nom;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite;

    @FXML
    private TextField reference;

    @FXML
    private Button update_btn;
    private int id;
    private ListEquipement listEquipementController;
    private EquipementAffichage equipement;

    public void setListEquipementController(ListEquipement listEquipementController) {
        this.listEquipementController = listEquipementController;
    }

    @FXML
    void ajouter_image(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get("C:\\Users\\ASUS\\Downloads\\pi3A21\\AutoHeaven\\src\\main\\resources\\dir\\", imageName);
            try {
                Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Erreur lors de la copie de l'image.");
            }
        }
    }

    public void initData(EquipementAffichage equipement) {
        this.equipement = equipement;
        this.id = equipement.getId();
        error_ref.setVisible(false);
        error_nom.setVisible(false);
        error_marque.setVisible(false);
        error_desc.setVisible(false);
        error_prix.setVisible(false);
        error_quantite.setVisible(false);
        error_image.setVisible(false);

        nom.setText(equipement.getNom());
        desc.setText(equipement.getDescription());
        marque.setText(equipement.getMarque());
        reference.setText(equipement.getReference());
        prix.setText(String.valueOf(equipement.getPrixvente()));
        quantite.setText(String.valueOf(equipement.getQuantite()));

        String imagePath = equipement.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File file = new File(imagePath);
                String localUrl = file.toURI().toURL().toString();
                Image image = new Image(localUrl);
                imageInput.setImage(image);
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image: " + e.getMessage());
                imageInput.setImage(null);
            }
        } else {
            System.err.println("Chemin d'image invalide ou vide.");
            imageInput.setImage(null);
        }
    }

    @FXML
    void Update(ActionEvent event) throws Exception {
        boolean check = false;

        String imagePath;
        if (selectedImageFile != null) {
            imagePath = "C:\\Users\\ASUS\\Downloads\\pi3A21\\AutoHeaven\\src\\main\\resources\\dir\\" + imageName;
        } else {
            imagePath = equipement.getImage();
        }

        if (imagePath == null || imagePath.isEmpty()) {
            error_image.setVisible(true);
            check = true;
        } else {
            h_error_image.setVisible(false);
        }

        if (nom.getText().isEmpty()) {
            error_nom.setVisible(true);
            check = true;
        } else {
            h_error_nom.setVisible(false);
        }

        if (prix.getText().isEmpty()) {
            error_prix.setVisible(true);
            check = true;
        } else {
            h_error_prix.setVisible(false);
        }

        if (quantite.getText().isEmpty()) {
            error_quantite.setVisible(true);
            check = true;
        } else {
            h_error_quantite.setVisible(false);
        }

        if (reference.getText().isEmpty()) {
            error_ref.setVisible(true);
        } else {
            h_error_ref.setVisible(false);
        }

        if (marque.getText().isEmpty()) {
            error_marque.setVisible(true);
            check = true;
        } else {
            h_error_marque.setVisible(false);
        }

        if (desc.getText().isEmpty()) {
            error_desc.setVisible(true);
            check = true;
        } else {
            error_desc.setVisible(false);
        }

        if (check) {
            return;
        }

        Equipement u1 = new Equipement(id, nom.getText(), desc.getText(), imagePath, reference.getText(), marque.getText());
        int quantite1 = Integer.parseInt(quantite.getText());
        double prix1 = Double.parseDouble(prix.getText());

        EquipementService sc = new EquipementService();
        sc.update(u1, quantite1, prix1);
       // listEquipementController.hideUpdatePopup();
        listEquipementController.reloadEquipements(sc.getAll());
        redirectToListEquipement();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'équipement a été mis à jour avec succès !");
        alert.showAndWait();
    }
    @FXML
    void closePopup(ActionEvent event) {
        listEquipementController.hideUpdatePopup();
    }

    @FXML
    void cancelUpdate(ActionEvent event) throws IOException {
       // listEquipementController.hideUpdatePopup();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
            Parent root = loader.load();
            Scene currentScene = update_btn.getScene();
            currentScene.setRoot(root);

    }
    private void redirectToListEquipement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
        Parent root = loader.load();
        Scene scene = update_btn.getScene();
        scene.setRoot(root);
    }
}