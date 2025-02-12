package org.example;
import models.Reservation;
import models.User;
import services.ReservationService;
import services.UserService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {

            /*
            UserService userService = new UserService();
            ReservationService reservationService = new ReservationService();

            User user = new User(
                    1,
                    14416924,
                    "Rayen",
                    "Elfil",
                    54227887,
                    "rayen.elfil@esprit.tn",
                    "test123",
                    "client");
            userService.create(user);
            System.out.println("Utilisateur ajoute avec succes : " + user);

            Reservation reservation = new Reservation();

            reservation.setDate_res(new Date());
            reservation.setStatus("en_revision");
            reservation.setUser(user);
            reservation.setId_v(2);
            reservationService.create(reservation);
            System.out.println("Réservation ajoutée avec succès : " + reservation);

            */

            //**********************************************************************************

            /*

            ReservationService reservationService = new ReservationService();

            int reservationId = 3;

            Reservation reservation = reservationService.getById(reservationId);
            if (reservation != null) {
                System.out.println("Ancienne réservation : " + reservation);
                reservation.setDate_res(new Date()); // Mettre à jour la date
                reservation.setStatus("Confirmée"); // Modifier le statut
                reservationService.update(reservation);

                System.out.println("Réservation mise à jour avec succès : " + reservation);
            } else {
                System.out.println("Aucune réservation trouvée avec l'ID : " + reservationId);
            }

            */

            //****************************************************************************************

            ReservationService reservationService = new ReservationService();

            int reservationId = 3; // Exemple : ID 1

            Reservation reservation = reservationService.getById(reservationId);

            if (reservation != null) {
                reservationService.delete(reservation);
                System.out.println("Réservation supprimée avec succès !");
            } else {
                System.out.println("Aucune réservation trouvée avec l'ID : " + reservationId);
            }

            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}