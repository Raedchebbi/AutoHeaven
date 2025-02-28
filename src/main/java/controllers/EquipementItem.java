package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.EquipementAffichage;
import models.Lignecommande;
import services.EquipementService;
import services.LigneCommandeService;

import java.io.IOException;
import java.util.List;

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
            LigneCommandeService ls = new LigneCommandeService();
            List<Lignecommande> lc =ls.getByIDE(equipement.getId());
            if (lc.size() > 0) {
                showErrorPopup("L'Equipement " + equipement.getNom() + " a été commandé par le client");

            }
            else {
                es.delete(equipement.getId());
            }
            if (listEquipementController != null) {
                List<EquipementAffichage> l=es.getAll();
                listEquipementController.reloadEquipements(l);
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
    private void showErrorPopup(String message) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}