package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Commande;
import models.EquipementAffichage;
import services.EquipementService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListEquipement implements Initializable {

    @FXML
    private Button add_btn;

    @FXML
    private AnchorPane popupContainer;

    @FXML
    private VBox equip_container;

    @FXML
    private HBox equip_header;

    @FXML
    private Pane equip_pa;

    @FXML
    private TextField input_search;


    @FXML
    private ComboBox<String> comCombo;
    EquipementService es=new EquipementService();
    List<EquipementAffichage> l =new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCombo();

        try {
            l=es.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            reloadEquipements(l);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        comCombo.setOnAction(event -> {
            try {
                String selectedValue = comCombo.getValue();
                List<EquipementAffichage> filteredList = new ArrayList<>();

                if (selectedValue.equals("Tous")) {

                    filteredList = l;
                } else if (selectedValue.equals("Nom")) {

                    filteredList = filterName();
                } else {


                    filteredList = filterStock();
                }


                reloadEquipements(filteredList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        input_search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue == null || newValue.trim().isEmpty()) {

                    List<EquipementAffichage> allEquipements = es.getAll();
                    reloadEquipements(allEquipements);
                } else {

                    List<EquipementAffichage> filtredName = handleSearch_name(newValue);
                    reloadEquipements(filtredName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void initCombo(){
        comCombo.getItems().add("Tous");
        comCombo.getItems().add("Nom");
        comCombo.getItems().add("Stock =0");

        comCombo.setValue("Tous");

        // comCombo.getSelectionModel().selectFirst();

    }

    public void reloadEquipements(List<EquipementAffichage>obs) throws Exception {
        equip_container.getChildren().clear();

        EquipementService es = new EquipementService();
       // List<EquipementAffichage> obs = es.getAll();

        for (EquipementAffichage e : obs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementItem.fxml"));
                Parent item = loader.load();
                EquipementItem ie = loader.getController();
                ie.initData(e);
                ie.setListEquipementController(this);
                equip_container.getChildren().add(item);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void showUpdatePopup(EquipementAffichage equipement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/update.fxml"));
            Parent updateEquip = loader.load();
            UpdateEquipCard updateEquipCardController = loader.getController();
            updateEquipCardController.setListEquipementController(this);
            updateEquipCardController.initData(equipement);

            equip_pa.getChildren().clear();
            equip_pa.getChildren().add(updateEquip);

            // Appliquer un effet de flou au Pane principal


            equip_pa.setVisible(true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hideUpdatePopup() {
        popupContainer.setVisible(false);
    }

    @FXML
    public void AjouterE() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEquipemntCard.fxml"));
        Parent addEquip = loader.load();
        equip_pa.getChildren().clear();
        equip_pa.getChildren().add(addEquip);
    }

    @FXML
    public void AjouterE(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEquipemntCard.fxml"));
        Parent addEquip = loader.load();

        if (equip_pa != null) {
            equip_pa.getChildren().clear();
            equip_pa.getChildren().add(addEquip);
        } else {
            System.out.println("Erreur : equipementPanel est null !");
        }
    }
    public List<EquipementAffichage> handleSearch_name(String searchText) throws Exception {
        List<EquipementAffichage> l = new ArrayList<>();
        l = es.getAll().stream()
                .filter(x -> x.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                        x.getMarque().toLowerCase().contains(searchText.toLowerCase()) ||
                        x.getReference().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        return l;
    }
    public List<EquipementAffichage> filterName() throws Exception {

        List<EquipementAffichage> filtered = es.getAll().stream().sorted(Comparator.comparing(EquipementAffichage::getNom)).collect(Collectors.toList());
        return filtered;

    }
    public  List<EquipementAffichage> filterStock() throws Exception {

            List<EquipementAffichage> filtred = es.getAll().stream()
                    .filter(c -> c.getQuantite()==0)
                    .collect(Collectors.toList());
            return filtred;
        }


}