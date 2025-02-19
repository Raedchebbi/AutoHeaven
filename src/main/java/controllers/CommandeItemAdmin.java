package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import models.Commande;
import models.EquipementAffichage;
import models.Lignecommande;
import models.User;
import services.CommandeService;
import services.LigneCommandeService;

import java.util.ArrayList;
import java.util.List;

public class CommandeItemAdmin {

    @FXML
    private Button cancel;

    @FXML
    private Button check;

    @FXML
    private Label date;

    @FXML
    private FontAwesomeIconView detail;

    @FXML
    private Label nom;

    @FXML
    private Label pren;

    @FXML
    private Label prix;

    @FXML
    private Label tel;
    private Commande commande;
    private ValidationCommande vc;

    public void initData(Commande commande) throws Exception {
        this.commande = commande;
        LigneCommandeService ls = new LigneCommandeService();
        User user =ls.getByID(commande.getId());
        nom.setText(user.getNom());
        pren.setText(user.getPrenom());
        tel.setText(String.valueOf(user.getTel()));
        date.setText(String.valueOf(commande.getDate_com()));
        //prix.setText(String.valueOf(commande.getMontant_total()));
        prix.setText(commande.getStatus());
        detail.setOnMouseClicked(event -> {
            try {
                handleDetailClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        check.setOnMouseClicked(event -> {
            try {
                handleCheckClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cancel.setOnMouseClicked(event -> {
            try {
                handleCancelClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void setListComController(ValidationCommande vc) {
        this.vc = vc;
    }
    private void handleDetailClick(MouseEvent event) throws Exception {
        // Récupérer les équipements achetés pour cette commande
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        List<Lignecommande> ligneCommandes = new ArrayList<>();
        System.out.println(ligneCommandeService.getAllByIDC(commande.getId_com()));
        for (Lignecommande l :ligneCommandeService.getAllByIDC(commande.getId_com())){
            ligneCommandes.add(l);

        }



        vc.showEquipementsAchetes(ligneCommandes ,commande);
    }
    private void handleCheckClick(MouseEvent event) throws Exception {
        // Mettre à jour le statut de la commande à "confirmee"
        CommandeService cs = new CommandeService();
        cs.updateStatus(commande.getId_com(), "confirmee");

        // Recharger la liste des commandes après la mise à jour
        vc.reloadCommande();
    }

    private void handleCancelClick(MouseEvent event) throws Exception {
        // Mettre à jour le statut de la commande à "annulee"
        CommandeService cs = new CommandeService();
        cs.updateStatus(commande.getId_com(), "annulee");

        // Recharger la liste des commandes après la mise à jour
        vc.reloadCommande();
    }

}
