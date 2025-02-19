package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Commande;
import models.EquipementAffichage;
import models.User;
import services.LigneCommandeService;

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
        prix.setText(String.valueOf(commande.getMontant_total()));

    }

    public void setListComController(ValidationCommande vc) {
        this.vc = vc;
    }


}
