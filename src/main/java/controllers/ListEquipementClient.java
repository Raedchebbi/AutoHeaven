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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    EquipementService es=new EquipementService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<EquipementAffichage> l =new ArrayList<>();
        try {
            l=es.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            reloadEquipements(l);
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
        input_search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue == null || newValue.trim().isEmpty()) {
                    // Si le champ de recherche est vide, recharger tous les équipements
                    List<EquipementAffichage> allEquipements = es.getAll();
                    reloadEquipements(allEquipements);
                } else {
                    // Sinon, effectuer la recherche
                    List<EquipementAffichage> filtredName = handleSearch_name(newValue);
                    reloadEquipements(filtredName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Écouteur pour le bouton de recherche
        search_btn.setOnMouseClicked(event -> {
            try {
                String searchText = input_search.getText();
                List<EquipementAffichage> filtredName = handleSearch_name(searchText);
                reloadEquipements(filtredName);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
    public void reloadEquipements(List<EquipementAffichage> obs) throws Exception {
        int column =0 ;
        int row=1;

        grid.getChildren().clear();

        //EquipementService es = new EquipementService();
        //List<EquipementAffichage> obs = es.getAll();

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
                System.out.println("Nombre d'équipements à afficher : " + obs.size());


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
   /* public List<EquipementAffichage> handleSearch_name(MouseEvent event) throws Exception {
        String nom= this.input_search.getText();
        List<EquipementAffichage> l =new ArrayList<>();
      //  l=es.getAll().stream().filter(x -> x.getNom().equals(nom)).collect(Collectors.toList());
        l=es.getAll().stream().filter(x->x.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
        System.out.println(l);
        if (l.isEmpty()){
            l=es.getAll().stream().filter(x->x.getMarque().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
            System.out.println(l);

        }

        System.out.println(l);
        return l;


    }*/
   public List<EquipementAffichage> handleSearch_name(String searchText) throws Exception {
       List<EquipementAffichage> l = new ArrayList<>();
       l = es.getAll().stream()
               .filter(x -> x.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                       x.getMarque().toLowerCase().contains(searchText.toLowerCase()) ||
                       x.getReference().toLowerCase().contains(searchText.toLowerCase()))
               .collect(Collectors.toList());
       return l;
   }


}
