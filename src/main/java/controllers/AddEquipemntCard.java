package controllers;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.Equipement;
import services.EquipementService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddEquipemntCard implements Initializable {

    @FXML
    private Button add_btn;

    @FXML
    private Button add_photo;

    @FXML
    private VBox area;


    @FXML
    private TextArea desc;

    @FXML
    private Text error_desc;

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
    private HBox h_error_marque;

    @FXML
    private HBox h_error_nom;

    @FXML
    private HBox h_error_photo;

    @FXML
    private HBox h_error_prix;

    @FXML
    private HBox h_error_quantite;

    @FXML
    private HBox h_error_ref;

    @FXML
    private TextField marque;

    @FXML
    private TextField nom;
    @FXML
    private Text error_photo;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite;
    private String imageName;
    @FXML
    private File selectedImageFile;

    @FXML
    private ImageView imageInput;
    private Button equipbtn;


    @FXML
    private TextField reference;
    public void setEquipbtn(Button equipbtn) {
        this.equipbtn = equipbtn;
    }
    private boolean ckeck ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        error_ref.setVisible(false);
        error_nom.setVisible(false);
        error_marque.setVisible(false);
        error_desc.setVisible(false);
        error_prix.setVisible(false);
        error_quantite.setVisible(false);
        error_photo.setVisible(false);

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

            Path destination = Paths.get("C:\\Users\\ASUS\\Downloads\\integration\\AutoHeaven\\src\\main\\resources\\dir", imageName);
            try {
                Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Erreur lors de la copie de l'image.");
            }
        }
    }

    @FXML
    void Ajouter(ActionEvent event) throws Exception {
        boolean check = false;
        // Récupérer le chemin de l'image
        String imagePath = "C:\\Users\\ASUS\\Downloads\\integration\\AutoHeaven\\src\\main\\resources\\dir\\" + imageName;
        if (imageName == null) {
            error_photo.setVisible(true);
            check=true;
        }
        else {
            h_error_photo.setVisible(false);

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


        Equipement u1 = new Equipement(nom.getText(), desc.getText(), imagePath, reference.getText(), marque.getText());
        int quantite1 = Integer.parseInt(quantite.getText());
        double prix1 = Double.parseDouble(prix.getText());


        EquipementService sc = new EquipementService();
        sc.create(u1, quantite1, prix1);


        showSuccessPopup();


        redirectToListEquipement();


    }

    private void showSuccessPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'équipement a été ajouté avec succès !");
        alert.showAndWait();
    }

    /*  private void redirectToListEquipement() throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
          Parent root = loader.load();
          Scene scene = add_btn.getScene();
          scene.setRoot(root);
      }*/
    private void redirectToListEquipement() throws IOException {
        // Chargez Dashboard1.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        Parent root = loader.load();
        dashboardController dashboardController = loader.getController();

        // Chargez ListEquipement dans le Dashboard
        dashboardController.loadListEquipementForm();

        // Mettez à jour la scène actuelle avec la nouvelle racine
        Scene scene = add_btn.getScene();
        scene.setRoot(root);
    }

    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
        Parent root = loader.load();
        nom.getScene().setRoot(root);
    }
    @FXML
    private void handleBackAction(ActionEvent event) throws IOException {

        // Charger le Dashboard1.fxml

        // listEquipementController.hideUpdatePopup();


           /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipement.fxml"));
            Parent root = loader.load();
            Scene currentScene = update_btn.getScene();
            currentScene.setRoot(root);
*/


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        Parent root = loader.load();
        dashboardController dashboardController = loader.getController();


        dashboardController.loadListEquipementForm();


        Scene scene = add_btn.getScene();
        scene.setRoot(root);

    }
}