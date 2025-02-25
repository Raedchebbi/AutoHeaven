package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Commande;
import models.Equipement;
import models.EquipementAffichage;
import models.Lignecommande;
import org.apache.poi.ss.formula.functions.T;
import services.CommandeService;
import services.EquipementService;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.LigneCommandeService;

import java.io.FileOutputStream;
import java.io.IOException;

public class ValidationCommande implements Initializable {

    @FXML
    private VBox area;

    @FXML
    private ScrollPane pane;
    @FXML
    private ComboBox<String> comCombo;
    @FXML
    private Button exportexcel;

    @FXML
    private Pane pane_1;
    CommandeService es = new CommandeService();
    List<Commande> l = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initCombo();
        try {
             l =es.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            reloadCommande(l);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        comCombo.setOnAction(event -> {
            try {
                String selectedValue = comCombo.getValue();
                List<Commande> filteredList = new ArrayList<>();

                if (selectedValue.equals("Tous")) {
                    // Recharger la liste complète
                    filteredList = l;
                } else if (selectedValue.equals("Date")) {
                    // Filtrer par date
                    filteredList = filterComDate();
                } else {
                    // Filtrer par statut
                    String status = getStatus();
                    filteredList = filterComAccept(status);
                }

                // Recharger les commandes avec la liste filtrée ou complète
                reloadCommande(filteredList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        exportexcel.setOnAction(event -> {
            try {

                // Recharger les commandes avec la liste filtrée ou complète
               ExportExcel(l,"datacommande");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void reloadCommande(List<Commande> obs) throws Exception {
        area.getChildren().clear();

        CommandeService es = new CommandeService();
        //List<Commande> obs = es.getAll();
        //System.out.println(obs.size());


        for (Commande e : obs) {
            try {
                System.out.println(e);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommandeItemAdmin.fxml"));
                Parent item = loader.load();
                CommandeItemAdmin ie = loader.getController();
                ie.initData(e);
                ie.setListComController(this);
                area.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void showEquipementsAchetes(List<Lignecommande> ligneCommandes ,Commande commande) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementAchetesadr.fxml"));
        Parent root = loader.load();


        EquipementAchetesadr controller = loader.getController();
        controller.initData(ligneCommandes,commande);


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Équipements Achetés");
        stage.show();
    }
    public void initCombo(){
        comCombo.getItems().add("Tous");
        comCombo.getItems().add("Date");
        comCombo.getItems().add("Acceptée");
        comCombo.getItems().add("Annulée");
        comCombo.getItems().add("En attente");
        comCombo.setValue("Tous");

       // comCombo.getSelectionModel().selectFirst();

    }
    public String  getStatus() {
        return switch (comCombo.getValue()) {
            case "Acceptée" ->"confirmee";
            case "Annulée" -> "annulee";
            case "En attente" -> "en attente";
            default -> "All";
        };
    }
    public List<Commande> filterComDate() throws Exception {

        List<Commande> filtered = es.getAll().stream().sorted(Comparator.comparing(Commande::getDate_com).reversed()).collect(Collectors.toList());
        return filtered;

    }
    public  List<Commande> filterComAccept(String Status) throws Exception {
        if (!Status.equals("All")) {
            List<Commande> filtred = es.getAll().stream()
                    .filter(c -> c.getStatus().equals(Status))
                    .collect(Collectors.toList());
            return filtred;
        }
        return es.getAll();
    }

    @FXML
    public void ExportExcel(List<Commande> list, String sheetName) throws Exception {
        // Créer une boîte de dialogue pour choisir l'emplacement et le nom du fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.setInitialFileName("Commandes.xlsx"); // Nom par défaut du fichier

        // Définir un filtre pour n'afficher que les fichiers Excel
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers Excel (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue et attendre que l'utilisateur choisisse un emplacement
        File file = fileChooser.showSaveDialog(pane_1.getScene().getWindow());

        // Si l'utilisateur annule la boîte de dialogue, ne rien faire
        if (file == null) {
            return;
        }

        // Chemin du fichier sélectionné par l'utilisateur
        String filePath = file.getAbsolutePath();

        try (Workbook workbook = new XSSFWorkbook()) {
            // Créer une feuille Excel
            Sheet sheet = workbook.createSheet(sheetName);

            // Créer la ligne d'en-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Commande");
            headerRow.createCell(1).setCellValue("Date Commande");
            headerRow.createCell(2).setCellValue("Statut");
            headerRow.createCell(3).setCellValue("Montant Total");
            headerRow.createCell(4).setCellValue("Équipements"); // Nouvelle colonne pour les équipements

            // Remplir les données des commandes
            for (int i = 0; i < list.size(); i++) {
                Commande commande = list.get(i);
                Row row = sheet.createRow(i + 1);

                // Ajouter les données de la commande
                row.createCell(0).setCellValue(commande.getId_com());
                row.createCell(1).setCellValue(commande.getDate_com().toString());
                row.createCell(2).setCellValue(commande.getStatus());
                row.createCell(3).setCellValue(commande.getMontant_total());

                // Récupérer les équipements de la commande
                LigneCommandeService ligneCommandeService = new LigneCommandeService();
                List<Lignecommande> ligneCommandes = ligneCommandeService.getAllByIDC(commande.getId_com());

                // Formater la liste des équipements en une chaîne de caractères
                StringBuilder equipements = new StringBuilder();
                for (Lignecommande ligne : ligneCommandes) {
                    EquipementService equipementService = new EquipementService();
                    Equipement equipement = equipementService.getEquipementById(ligne.getId_e());
                    equipements.append(equipement.getNom()).append(" (Quantité: ").append(ligne.getQuantite()).append("), ");
                }

                // Supprimer la virgule finale
                if (equipements.length() > 0) {
                    equipements.setLength(equipements.length() - 2);
                }

                // Ajouter les équipements dans la colonne correspondante
                row.createCell(4).setCellValue(equipements.toString());
            }

            // Sauvegarder le fichier Excel
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            // Afficher une confirmation
            showAlert("Export réussi", "Le fichier Excel a été enregistré avec succès à l'emplacement : " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'exportation vers Excel : " + e.getMessage());
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }


