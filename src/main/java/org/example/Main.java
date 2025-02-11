package org.example;

import models.Reservation;
import services.ReservationService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ReservationService reservationService = new ReservationService();

        try {

            // Create
            Reservation reservation = new Reservation(new Date(), "En Traitement", 1, 101);
            /*reservationService.create(reservation);
            System.out.println("Reservation cree : " + reservation);
            */
            // Update
            reservation.setStatus("Approuvee");
            reservationService.update(reservation);
            System.out.println("Réservation mise à jour : " + reservation);
            /*
            // Delete
            reservationService.delete(reservation);
            System.out.println("Réservation supprimée.");
            */

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}