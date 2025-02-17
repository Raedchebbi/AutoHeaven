package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.EquipementAffichage;
import services.EquipementService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EquipementItem {
    @FXML
    private HBox equip_item;

    @FXML
    private Label reference;

    @FXML
    private Label nom;

    @FXML
    private Label marque;

    @FXML
    private Label prix;

    @FXML
    private Label quantite;

    @FXML
    private Button delete_btn;

    private EquipementAffichage equipement;
    private ListEquipement listEquipementController;

    public void initData(EquipementAffichage equipement) {
        this.equipement = equipement;
        reference.setText(equipement.getReference());
        nom.setText(equipement.getNom());
        marque.setText(equipement.getMarque());
        prix.setText(String.valueOf(equipement.getPrixvente()));
        quantite.setText(String.valueOf(equipement.getQuantite()));

    }

    public void setListEquipementController(ListEquipement listEquipementController) {
        this.listEquipementController = listEquipementController;
    }

    @FXML
    private void handleDeleteAction() throws Exception {
        if (equipement != null) {
            System.out.println("Tentative de suppression de l'équipement avec l'ID : " + equipement.getId());
            EquipementService es = new EquipementService();
            es.delete(equipement.getId());


            if (listEquipementController != null) {

                listEquipementController.reloadEquipements();
            } else {
                System.out.println("Erreur : listEquipementController est null !");
            }
        } else {
            System.out.println("Erreur : equipement est null !");
        }
    }

    @FXML
    private void handleEditAction() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEquipCard.fxml"));
        Parent root = loader.load();


        UpdateEquipCard controller = loader.getController();

        controller.initData(equipement);


        controller.setListEquipementController(listEquipementController);


        Scene scene = new Scene(root);

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);


        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        shadow.setRadius(10);
        shadow.setSpread(0.5);
        root.setEffect(shadow);

        popupStage.setScene(scene);


        popupStage.show();
    }
    @FXML
    void handleEditAction(ActionEvent event) {
        listEquipementController.showUpdatePopup(equipement);
    }
}
