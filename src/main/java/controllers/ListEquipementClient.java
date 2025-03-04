package controllers;

import javafx.event.ActionEvent;
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

    private profileController dashboardController;  // Référence au contrôleur du Dashboard

    private boolean isInitialized = false;

    public void setDashboardController(profileController dashboardController) {
        System.out.println("Setting DashboardController in ListEquipementClient: " + (dashboardController != null ? "not null" : "null"));
        this.dashboardController = dashboardController;
        if (!isInitialized) {
            initializeData(); // Initialiser les données une fois dashboardController est défini
        }
    }


    EquipementService es=new EquipementService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Ne rien faire ici pour éviter d’appeler reloadEquipements avant que dashboardController ne soit défini
        System.out.println("ListEquipementClient initialized. DashboardController: " + (dashboardController != null ? "not null" : "null"));

        panier.setOnMouseClicked(event -> {
            try {
                handlePanierClick(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        input_search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (dashboardController != null && (newValue == null || newValue.trim().isEmpty())) {
                    List<EquipementAffichage> allEquipements = new EquipementService().getAll();
                    reloadEquipements(allEquipements);
                } else if (dashboardController != null) {
                    List<EquipementAffichage> filtredName = handleSearch_name(newValue);
                    reloadEquipements(filtredName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        search_btn.setOnMouseClicked(event -> {
            try {
                if (dashboardController != null) {
                    String searchText = input_search.getText();
                    List<EquipementAffichage> filtredName = handleSearch_name(searchText);
                    reloadEquipements(filtredName);
                }
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

    private void initializeData() {
        EquipementService es = new EquipementService();
        List<EquipementAffichage> l = new ArrayList<>();
        try {
            l = es.getAll();
            reloadEquipements(l);
            isInitialized = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void handlePanierClick(MouseEvent event) throws IOException {

       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/Paniers.fxml"));
        Parent root = loader.load();



        Scene scene = new Scene(root);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        stage.setScene(scene);
        stage.show();*/
        System.out.println("Panier button clicked!");
        if (dashboardController != null) {
            System.out.println("DashboardController is not null, loading Paniers form...");
            dashboardController.loadPaniersForm();
        } else {
            System.err.println("DashboardController is null!");
        }
    }
    public void reloadEquipements(List<EquipementAffichage> obs) throws Exception {
        System.out.println("Reloading equipements in ListEquipementClient. DashboardController: " + (dashboardController != null ? "not null" : "null"));
        if (dashboardController == null) {
            System.err.println("DashboardController is null in ListEquipementClient! Skipping reload.");
            return; // ou lancer une exception si cela n’est pas acceptable
        }

        int column = 0;
        int row = 1;

        grid.getChildren().clear();

        for (EquipementAffichage e : obs) {
            try {
                FXMLLoader fxml1 = new FXMLLoader(getClass().getResource("/equipItemClient.fxml"));
                AnchorPane item = fxml1.load();
                EquipItemClient ie = fxml1.getController();
                ie.initData(e);
                if (dashboardController != null) {
                    ie.setDashboardController(dashboardController);
                    System.out.println("DashboardController passed to EquipItemClient: not null");
                } else {
                    System.err.println("DashboardController is null in ListEquipementClient! This should not happen.");
                    throw new RuntimeException("DashboardController is null in ListEquipementClient. Check initialization.");
                }

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(item, column++, row);
                GridPane.setMargin(item, new Insets(20, 20, 20, 20));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void handleCommandeClick(MouseEvent event) throws IOException {

      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/historiqueCommande.fxml"));
        Parent root = loader.load();

        //Parent root = loader.load();
        Scene currentScene =search_btn.getScene();
        currentScene.setRoot(root);*/
        System.out.println("Commande button clicked!");
        if (dashboardController != null) {
            System.out.println("DashboardController is not null, loading Paniers form...");
            dashboardController.loadComForm();
        } else {
            System.err.println("DashboardController is null!");
        }

        // Scene scene = new Scene(root);


        // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        // stage.setScene(scene);
        // stage.show();
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
