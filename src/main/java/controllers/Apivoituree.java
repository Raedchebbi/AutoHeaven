package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import services.Apivoiture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Apivoituree {
    @FXML
    private ComboBox<String> makeComboBox;
    @FXML
    private ComboBox<String> modelComboBox;
    @FXML
    private TextArea resultTextArea;

    @FXML
    public void initialize() {
        // Load available makes (brands) when UI initializes
        List<String> makes = Apivoiture.getMakes();
        makeComboBox.getItems().addAll(makes);

        // When a make is selected, load models
        makeComboBox.setOnAction(event -> {
            String selectedMake = makeComboBox.getValue();
            if (selectedMake != null) {
                List<String> models = Apivoiture.getModels(selectedMake);
                modelComboBox.getItems().clear();
                modelComboBox.getItems().addAll(models);
            }
        });
    }

    @FXML
    public void searchCarInfo() {
        String make = makeComboBox.getValue();
        String model = modelComboBox.getValue();

        if (make == null || model == null) {
            resultTextArea.setText("Veuillez sélectionner une marque et un modèle.");
            return;
        }

        // Check if a VIN is available
        String vin = "WBAFR7C57CC811956"; // Replace with logic to get a VIN
        if (vin == null || vin.isEmpty()) {
            resultTextArea.setText("Un VIN est requis pour obtenir les spécifications.");
            return;
        }

        String carSpecs = Apivoiture.getCarSpecs(vin);
        resultTextArea.setText(carSpecs);
    }
    public static List<String> getMakes() {
        return Arrays.asList("Toyota", "Honda", "Ford", "BMW");
    }

    public static List<String> getModels(String make) {
        switch (make) {
            case "Toyota":
                return Arrays.asList("Corolla", "Camry", "Rav4");
            case "Honda":
                return Arrays.asList("Civic", "Accord", "CR-V");
            case "Ford":
                return Arrays.asList("Mustang", "F-150", "Explorer");
            case "BMW":
                return Arrays.asList("X5", "3 Series", "5 Series");
            default:
                return Collections.emptyList();
        }
    }
}
