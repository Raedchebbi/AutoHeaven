package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class NotificationItem {
    @FXML
    private Label date;

    @FXML
    private HBox item;

    @FXML
    private Text message;

    @FXML
    private FontAwesomeIconView status;

}
