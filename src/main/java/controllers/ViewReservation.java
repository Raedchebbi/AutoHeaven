package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Reservation;
import services.ReservationService;

import java.util.List;

public class ViewReservation {

    @FXML
    private VBox reservationsContainer;
    @FXML
    private Button refreshButton;

    private ReservationService reservationService;

    @FXML
    public void initialize() {
        reservationService = new ReservationService();
        loadReservation();
    }

    @FXML
    private void handleRefresh() {
        loadReservation();
    }

    private void loadReservation() {
        reservationsContainer.getChildren().clear();

        try {
            List<Reservation> reservations = reservationService.getAll();
            for (Reservation reservation : reservations) {
                HBox reservationEntry = new HBox(10);

                Label idLabel = new Label("ID: " + reservation.getId_r());
                Label dateLabel = new Label("Date: " + reservation.getDate_res());
                Label statusLabel = new Label("Status: " + reservation.getStatus());
                Label voitureIdLabel = new Label("Voiture ID: " + reservation.getId_v());
                Label userIdLabel = new Label("User ID: " + reservation.getId());

                reservationEntry.getChildren().addAll(idLabel, dateLabel, statusLabel, voitureIdLabel, userIdLabel);

                reservationsContainer.getChildren().add(reservationEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}