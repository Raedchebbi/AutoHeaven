package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.Commande;
import models.EquipementAffichage;
import services.CommandeService;
import services.EquipementService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ValidationCommande implements Initializable {

    @FXML
    private HBox area;

    @FXML
    private ScrollPane pane;

    @FXML
    private Pane pane_1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reloadCommande();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadCommande() throws Exception {
        area.getChildren().clear();

        CommandeService es = new CommandeService();
        List<Commande> obs = es.getAll();
        System.out.println(obs.size());


        for (Commande e : obs) {
            try {
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
}
