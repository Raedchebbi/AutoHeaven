package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.EquipementAffichage;
import services.EquipementService;

import java.io.IOException;

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
    private void handleEditAction(ActionEvent event) {
        if (listEquipementController != null) {
            listEquipementController.showUpdatePopup(equipement);
        } else {
            System.out.println("Erreur : listEquipementController est null !");
        }
    }
}