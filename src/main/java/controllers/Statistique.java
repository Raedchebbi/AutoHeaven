package controllers;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import services.CommandeService;
import services.LigneCommandeService;
import services.UserService;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Statistique implements Initializable {
    @FXML
    private LineChart<String, Number> lineChartVentes;

    @FXML
    private PieChart pieCom;
    @FXML
    private CategoryAxis xAxisVentes;
    @FXML
    private Label nbClient;

    @FXML
    private Label nbCom;
    @FXML
    private BarChart<String, Number> barChartTopEquipements;
    @FXML
    private CategoryAxis xAxisEquipements;
    @FXML
    private NumberAxis yAxisVentes;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //nbClient.setText(String.valueOf(nbrClient()));
            nbCom.setText(String.valueOf(nbrCom()));
            loadPieChart();
            loadPieChartVentes();
            loadTopEquipements();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public void loadPieChart() throws Exception {
        CommandeService cs=new CommandeService();
        Map<String ,Integer> data = cs.countCOM();
        System.out.println(data);
       // Map.Entry<String , Integer>entry = data.entrySet().iterator().next();
       for( Map.Entry<String , Integer>entry : data.entrySet()){

        PieChart pieChart=new PieChart();
        PieChart.Data slice = new PieChart.Data(entry.getKey(),entry.getValue());
       pieCom.getData().add(slice);
       }
       // pieChart.setLegendVisible(false);
       // pieChart.setTitle("Vente");
       /// pieChart.setAnimated(false);
        //pieChart.setPrefSize(800, 600);


    }
    public void loadPieChartVentes() throws Exception {
     /*   CommandeService cs = new CommandeService();
        Map<String, Double> data = cs.countVente();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventes");

        xAxisVentes.getCategories().clear(); // Nettoyer les anciennes catégories

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            String mois = entry.getKey();  // Récupère le mois "YYYY-MM"
            xAxisVentes.getCategories().add(mois);
            series.getData().add(new XYChart.Data<>(mois, entry.getValue()));
        }

        lineChartVentes.getData().clear(); // Nettoyer les anciennes données
        lineChartVentes.getData().add(series);*/
        try {

            CommandeService cs = new CommandeService();
            Map<String, Double> data = cs.countVente();


            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Ventes par mois");


            int index=1;
            for (Map.Entry<String, Double> entry : data.entrySet()) {
               // double total = data.getOrDefault(mois, 0.0);  // Si le mois n'a pas de données, utiliser 0
                series.getData().add(new XYChart.Data<>(String.valueOf(index++), entry.getValue()));
            }

            // Ajouter la série au LineChart
            lineChartVentes.getData().add(series);

            // Optionnel : Configurer l'axe X pour afficher tous les mois
          /*  xAxisVentes.setCategories(FXCollections.observableArrayList(
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
            ));*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int nbrClient() throws Exception {
        UserService us=new UserService();
        int nb= (int) us.getAll().stream().filter(u->u.getRole().compareTo("client")==0).count();
        return nb;

    }
    public int nbrCom() throws Exception {
        CommandeService cs=new CommandeService();
        int nbc =cs.getAll().size();
        return nbc;
    }
    public void loadTopEquipements() throws Exception {
        LigneCommandeService cs = new LigneCommandeService();
        Map<String, Integer> topEquipements = cs.getTop5Equipements();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top 5 équipements vendus");

        xAxisEquipements.getCategories().clear();

        for (Map.Entry<String, Integer> entry : topEquipements.entrySet()) {
            String equipement = entry.getKey();
            int ventes = entry.getValue();

            xAxisEquipements.getCategories().add(equipement);
            series.getData().add(new XYChart.Data<>(equipement, ventes));
        }

        barChartTopEquipements.getData().clear();
        barChartTopEquipements.getData().add(series);
    }


}
