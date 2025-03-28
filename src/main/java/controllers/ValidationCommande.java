package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Commande;
import models.Equipement;

import models.Lignecommande;

import org.apache.poi.ss.usermodel.*;
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

                    filteredList = l;
                } else if (selectedValue.equals("Date")) {

                    filteredList = filterComDate();
                } else {

                    String status = getStatus();
                    filteredList = filterComAccept(status);
                }


                reloadCommande(filteredList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        exportexcel.setOnAction(event -> {
            try {


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
        comCombo.getItems().add("Traitée");
        comCombo.getItems().add("Expédiée");

        comCombo.getItems().add("En attente");
        comCombo.setValue("Tous");

       // comCombo.getSelectionModel().selectFirst();

    }
    public String  getStatus() {
        return switch (comCombo.getValue()) {
            case "Traitée" ->"traitée";

            case "En attente" -> "en attente";
            case "Expédiée"->"expédiée";
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

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.setInitialFileName("Commandes.xlsx");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Excel (*.xlsx)", "*.xlsx"));

        File file = fileChooser.showSaveDialog(pane_1.getScene().getWindow());
        if (file == null) {
            return;
        }

        String filePath = file.getAbsolutePath();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Date Commande");
            headerRow.createCell(1).setCellValue("Statut");
            headerRow.createCell(2).setCellValue("Montant Total");
            headerRow.createCell(3).setCellValue("Équipements");

            double totalGeneral = 0;

            for (int i = 0; i < list.size(); i++) {
                Commande commande = list.get(i);
                Row row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(commande.getDate_com().toString());
                row.createCell(1).setCellValue(commande.getStatus());
                row.createCell(2).setCellValue(commande.getMontant_total());

                totalGeneral += commande.getMontant_total();


                LigneCommandeService ligneCommandeService = new LigneCommandeService();
                List<Lignecommande> ligneCommandes = ligneCommandeService.getAllByIDC(commande.getId_com());

                StringBuilder equipements = new StringBuilder();
                for (Lignecommande ligne : ligneCommandes) {
                    EquipementService equipementService = new EquipementService();
                    Equipement equipement = equipementService.getEquipementById(ligne.getId_e());
                    equipements.append(equipement.getNom()).append(" (Quantité: ").append(ligne.getQuantite()).append("), ");
                }

                if (equipements.length() > 0) {
                    equipements.setLength(equipements.length() - 2);
                }

                row.createCell(3).setCellValue(equipements.toString());
            }


            int lastRowNum = list.size() + 1;
            Row totalRow = sheet.createRow(lastRowNum);
            totalRow.createCell(1).setCellValue("TOTAL");
            totalRow.createCell(2).setCellValue(totalGeneral);


            CellStyle boldStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            boldStyle.setFont(boldFont);
            totalRow.getCell(1).setCellStyle(boldStyle);
            totalRow.getCell(2).setCellStyle(boldStyle);


            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            showAlert("Export réussi", "Le fichier Excel a été enregistré avec succès à l'emplacement : " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'exportation vers Excel : " + e.getMessage());
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }


