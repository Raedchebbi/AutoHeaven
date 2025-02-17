package org.example;

import services.UserService;
import models.Reclamation;
import models.Messagerie;
import services.ReclamationService;
import services.MessagerieService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class mainreclamation {
    public static void main(String[] args) {
        UserService userService = new UserService();
        ReclamationService reclamationService = new ReclamationService();
        MessagerieService messagerieService = new MessagerieService();

        try {
            /////////////////////////// ==== TEST MESSAGERIE ====
            Messagerie message = new Messagerie(0, "client", "Bonsoir, comment allez-vous ?", 44, LocalDateTime.now(), "admin", 3);
            messagerieService.create(message);
            System.out.println("✅ Message envoyé !");
            System.out.println(messagerieService.getAll());

            /////////////////////////////// ==== TEST UPDATE MESSAGERIE ====
            Messagerie updatedMessage = new Messagerie(58, "client", "Message mis à jour !", 44, LocalDateTime.now(), "client", 3);
            messagerieService.update(updatedMessage);
            System.out.println("✅ Message mis à jour !");
            System.out.println(messagerieService.getAll());

            ////////////////////////// ==== TEST DELETE MESSAGERIE ====
            int messageIdToDelete = 76; // ID du message à supprimer
            messagerieService.deleteById(messageIdToDelete);
            System.out.println("✅ Message supprimé !");
            System.out.println(messagerieService.getAll());

            /////////////////////////// ==== TEST RECLAMATION ====
            Reclamation reclamation = new Reclamation(0, "Problème de connexion", "Je n'arrive pas à me connecter.", "rejetee", LocalDate.now(), 4);
            reclamationService.create(reclamation);
            System.out.println("✅ Réclamation créée !");
            System.out.println(reclamationService.getAll());

            ////////////////////////// ==== TEST UPDATE RECLAMATION ====
            Reclamation updatedReclamation = new Reclamation(44, "Problème de connexion", "Connexion rétablie, mise à jour !", "acceptee", LocalDate.now(), 4);
            reclamationService.update(updatedReclamation);
            System.out.println("✅ Réclamation mise à jour !");
            System.out.println(reclamationService.getAll());

            ////////////////////////////// ==== TEST DELETE RECLAMATION ====
            int reclamationIdToDelete = 74;
            reclamationService.deleteById(reclamationIdToDelete);
            System.out.println("✅ Réclamation supprimée !");
            System.out.println(reclamationService.getAll());

        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
