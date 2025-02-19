package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Commande;
import models.Lignecommande;
import models.Panier;
import services.CommandeService;
import services.PanierService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoriqueCommande implements Initializable {

    @FXML
    private VBox area;
    Commande commande;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de HistoriqueCommande");
        try {
            reloadcom();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadcom() throws Exception {
        area.getChildren().clear();

        CommandeService ps = new CommandeService();
        List<Commande> obs = ps.getAllByidU(3);
        System.out.println("Nombre de commandes chargées : " + obs.size());

        for (Commande e : obs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/historiqueComItem.fxml"));
                Parent item = loader.load();
                HistoriqueComItem ie = loader.getController();
                ie.initData(e);
                ie.setListCommandeController(this);

                area.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }}
    public void showEquipementsAchetes(List<Lignecommande> ligneCommandes) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementsAchetes.fxml"));
        Parent root = loader.load();


        EquipementsAchetes controller = loader.getController();
        controller.initData(ligneCommandes);


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Équipements Achetés");
        stage.show();
    }
}
