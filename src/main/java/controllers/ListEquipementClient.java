package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.EquipementAffichage;
import services.EquipementService;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListEquipementClient implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private ImageView panier;

    @FXML
    private TextField input_search;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button search_btn;
    @FXML
    private Label commande;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reloadEquipements();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        panier.setOnMouseClicked(event -> {
            try {
                handlePanierClick(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        commande.setOnMouseClicked(event -> {
            try {
                handleCommandeClick(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    public void handlePanierClick(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Paniers.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        stage.setScene(scene);
        stage.show();
    }
    public void reloadEquipements() throws Exception {
        int column =0 ;
        int row=1;

        grid.getChildren().clear();

        EquipementService es = new EquipementService();
        List<EquipementAffichage> obs = es.getAll();

        for (EquipementAffichage e : obs) {
            try {
                FXMLLoader fxml1 =new FXMLLoader();
                // FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipItemClient.fxml"));
                fxml1.setLocation(getClass().getResource("/equipItemClient.fxml"));
                AnchorPane item = fxml1.load();
                //AnchorPane item = loader.load();
                //EquipItemClient ie = loader.getController();
                EquipItemClient ie = fxml1.getController();
                ie.initData(e);
                //ie.setListEquipementController(this);  // Passer la référence du contrôleur parent

                if(column==3){
                    column=0;
                    row++;
                }
                grid.add(item,column++,row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);


                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(item,new Insets(20,20,20,20));
                System.out.println("Adding item at column: " + column + ", row: " + row);


            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // grid.autosize();
    }
    public void handleCommandeClick(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/historiqueCommande.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        stage.setScene(scene);
        stage.show();
    }


}
