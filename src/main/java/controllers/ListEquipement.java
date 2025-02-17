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
    private VBox equip_container;

    @FXML
    private HBox equip_header;

    @FXML
    private Pane equip_pa;

    @FXML
    private TextField search_id;



    /*public void initialize(URL url, ResourceBundle resourceBundle) {
        EquipementService es = new EquipementService();

        ObservableList<EquipementAffichage> obs;
        try {
            obs = FXCollections.observableList(es.getAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (EquipementAffichage e : obs) {
            try {
                Stage primaryStage = new Stage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementItem.fxml"));
                //fxmlLoader.setRoot(equipementPane);
                Parent item = loader.load();
                //Scene scene = new Scene(item);
               // primaryStage.setTitle("AutoHeaven");
               // primaryStage.setScene(scene);
                EquipementItem ie = loader.getController();
                ie.initData(e);
                equip_container.getChildren().add(item);


            } catch (IOException e1) {
                e1.printStackTrace();

            }


        }}*/

    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EquipementService es = new EquipementService();
       // ObservableList<EquipementAffichage> obs ;
        // obs = FXCollections.observableArrayList();
         List<EquipementAffichage> obs =new ArrayList<>();

        try {
            obs.addAll(es.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (EquipementAffichage e : obs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementItem.fxml"));
                Parent item = loader.load();
                EquipementItem ie = loader.getController();
                ie.initData(e);

                // Vérifier si equip_container est bien initialisé
                if (equip_container != null) {
                    equip_container.getChildren().add(item);
                } else {
                    System.out.println("⚠ ERREUR : equip_container est null !");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


     public void loadEquipItem (ObservableList < EquipementAffichage > obs) {
            for (EquipementAffichage e : obs) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/equipement.fxml"));
                    //fxmlLoader.setRoot(equipementPane);
                    Parent item = loader.load();
                    EquipementItem ie = loader.getController();
                    ie.initData(e);
                    equip_container.getChildren().add(item);


                } catch (IOException e1) {
                    e1.printStackTrace();

                }
            }}*/
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
                ie.setListEquipementController(this);  // Passer la référence du contrôleur parent
                equip_container.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
