package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Offre;
import services.OffreService;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class offreController implements Initializable {
    @FXML
    private AnchorPane card_form;

    @FXML
    private Button checkBtn;

    @FXML
    private ImageView equipImg;

    @FXML
    private Label nomLabel;

    @FXML
    private Label tauxLabel;

    private Offre offre;
    private Image image;

    public void setData(Offre offre) {
        this.offre = offre;

        nomLabel.setWrapText(true);
        nomLabel.setText(offre.getType_offre());
        tauxLabel.setText("-" + (int) offre.getTaux_reduction() + "%");

        OffreService offreService = new OffreService();
        String imageUrl = offreService.getImageUrlForEquipement(offre.getId_equipement());


        if (imageUrl != null && !imageUrl.isEmpty()) {
            if (imageUrl.startsWith("http") || imageUrl.startsWith("https")) {
                // Load the image from a URL
                equipImg.setImage(new Image(imageUrl, 200, 180, false, true));
            } else {
                // Load the image from a file
                File file = new File(imageUrl);
                if (file.exists()) {
                    equipImg.setImage(new Image(file.toURI().toString(), 200, 180, false, true));
                } else {
                    equipImg.setImage(new Image("file:/path/to/placeholder.png"));
                }
            }
        } else {
            equipImg.setImage(new Image("file:/path/to/placeholder.png"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

}
