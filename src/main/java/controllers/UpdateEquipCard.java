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

            Path destination = Paths.get("C:\\Users\\ASUS\\Downloads\\pi3A21\\AutoHeaven\\src\\main\\resources\\dir", imageName);
            try {
                Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Erreur lors de la copie de l'image.");
            }
        }
    }


    public void initData(EquipementAffichage equipement){
        this.id=equipement.getId();
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
        // imageInput.setImage(new Image(equipement.getImage()));
        String imagePath = equipement.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Image image = new Image("file:" + imagePath);
                imageInput.setImage(image);
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image: " + e.getMessage());
                imageInput.setImage(null); //  mettre une image par défaut en cas d'erreur
            }
        } else {
            System.err.println("Chemin d'image invalide ou vide.");
            imageInput.setImage(null); //mettre une image par défaut
        }

    }
    @FXML
    void Update(ActionEvent event) throws Exception {
        boolean check = false;
        // Récupérer le chemin de l'image
        String imagePath = "C:\\Users\\ASUS\\Downloads\\pi3A21\\AutoHeaven\\src\\main\\resources\\dir" + imageName;
        if (imageName == null) {
            error_image.setVisible(true);
            check=true;
        }
        else {
            h_error_image.setVisible(false);

        }
        if(nom.getText().isEmpty()){
            error_nom.setVisible(true);
            check=true;
        }
        else {
            h_error_nom.setVisible(false);
        }
        if(prix.getText().isEmpty()){
            error_prix.setVisible(true);
            check=true;
        }
        else {
            h_error_prix.setVisible(false);
        }
        if(quantite.getText().isEmpty()){
            error_quantite.setVisible(true);
            check=true;
        }
        else {
            h_error_quantite.setVisible(false);
        }
        if(reference.getText().isEmpty()){
            error_ref.setVisible(true);
        }
        else {
            h_error_ref.setVisible(false);
        }
        if(marque.getText().isEmpty()){
            error_marque.setVisible(true);
            check=true;
        }
        else {
            h_error_marque.setVisible(false);
        }
        if (desc.getText().isEmpty()) {
            error_desc.setVisible(true);
            check=true;

        }
        else {
            error_desc.setVisible(false);
        }
        if(check){
            return ;
        }

        // Créer l'objet Equipement avec le chemin de l'image
        Equipement u1 = new Equipement(id,nom.getText(), desc.getText(), imagePath, reference.getText(), marque.getText());
        int quantite1 = Integer.parseInt(quantite.getText());
        double prix1 = Double.parseDouble(prix.getText());

        // Ajouter l'équipement
        EquipementService sc = new EquipementService();
        sc.update(u1, quantite1, prix1);

        // Afficher une pop-up de succès
        showSuccessPopup();

        // Rediriger vers ListEquipement.fxml
        //redirectToListEquipement();


    }

    private void showSuccessPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'équipement a été ajouté avec succès !");
        alert.showAndWait();
    }

    private void redirectToListEquipement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
        Parent root = loader.load();
        Scene scene = update_btn.getScene(); // Récupérer la scène actuelle
        scene.setRoot(root); // Définir la nouvelle racine de la scène
    }

    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
        Parent root = loader.load();
        nom.getScene().setRoot(root);
    }

}
