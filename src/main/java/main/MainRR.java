package main;

import models.Reservation;
import models.Remorquage;
import models.User;
import models.Voiture;
import services.ReservationService;
import services.RemorquageService;
import services.UserService;
import services.VoitureService;

import java.util.Date;
import java.util.List;

public class MainRR {
    public static void main(String[] args) {
        try {
            // Initialisation des services
            UserService userService = new UserService();
            VoitureService voitureService = new VoitureService();
            ReservationService reservationService = new ReservationService();
            RemorquageService remorquageService = new RemorquageService();

            // Récupérer tous les utilisateurs
            List<User> users = userService.getAll();
            if (users.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé !");
                return;
            }
            User user = users.get(0); // Prendre le premier utilisateur

            // Récupérer toutes les voitures
            List<Voiture> voitures = voitureService.getAll();
            if (voitures.isEmpty()) {
                System.out.println("Aucune voiture trouvée !");
                return;
            }
            Voiture voiture = voitures.get(0); // Prendre la première voiture

            // Test de la réservation
            Reservation reservation = new Reservation(new Date(), "Confirmée", voiture, user);
            reservationService.create(reservation);
            System.out.println("Réservation ajoutée : " + reservation);

            // Test du remorquage
            Remorquage remorquage = new Remorquage();
            remorquage.setUser(user);
            remorquage.setVoiture(voiture);
            remorquage.setDate_demande(new Date());
            remorquage.setAdresse("456 Elm St");
            remorquage.setDestination("Garage");
            remorquage.setStatut("en_attente");
            remorquage.setPrix(150.0);
            remorquageService.create(remorquage);
            System.out.println("Demande de remorquage ajoutée : " + remorquage);

            // Afficher toutes les réservations
            System.out.println("Liste des réservations : " + reservationService.getAll());

            // Afficher toutes les demandes de remorquage
            System.out.println("Liste des remorquages : " + remorquageService.getAll());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}