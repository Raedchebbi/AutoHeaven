package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.EquipementAffichage;
import services.EquipementService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListEquipement implements Initializable {

    @FXML
    private Button add_btn;

    @FXML
    private Pane popupContainer;

    @FXML
    private VBox equip_container;

    @FXML
    private HBox equip_header;

    @FXML
    private Pane equip_pa;

    @FXML
    private TextField search_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reloadEquipements();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadEquipements() throws Exception {
        equip_container.getChildren().clear();

        EquipementService es = new EquipementService();
        List<EquipementAffichage> obs = es.getAll();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEquipCard.fxml"));
            Parent updateEquip = loader.load();
            UpdateEquipCard updateEquipCardController = loader.getController();
            updateEquipCardController.setListEquipementController(this);
            updateEquipCardController.initData(equipement);

            popupContainer.getChildren().clear();
            popupContainer.getChildren().add(updateEquip);
            popupContainer.setVisible(true);
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
}