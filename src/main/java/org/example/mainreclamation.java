package org.example;

import services.UserService;
import models.Reclamation;
import models.Messagerie;
import services.ReclamationService;
import services.MessagerieService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class mainreclamation { // Nom de classe en PascalCase

    public static void main(String[] args) {
        UserService userService = new UserService();
        ReclamationService reclamationService = new ReclamationService();
        MessagerieService messagerieService = new MessagerieService();

        try {
            ///////////////////////////
            // TEST MESSAGERIE
            System.out.println("\n=== TEST MESSAGERIE ===");
            Messagerie message = new Messagerie(0, "client", "Bonsoir, comment allez-vous ?",
                    86, LocalDateTime.now(), "admin", 3);
            messagerieService.create(message);
            System.out.println("✅ Message créé : " + message);

            ///////////////////////////
            // TEST UPDATE MESSAGERIE
            System.out.println("\n=== TEST UPDATE MESSAGERIE ===");
            Messagerie updatedMessage = new Messagerie(58, "client", "Message mis à jour !",
                    86, LocalDateTime.now(), "client", 3);
            messagerieService.update(updatedMessage);
            System.out.println("✅ Message mis à jour : " + updatedMessage);

            ///////////////////////////
            // TEST DELETE MESSAGERIE
            System.out.println("\n=== TEST DELETE MESSAGERIE ===");
            int messageIdToDelete = 76;
            messagerieService.deleteById(messageIdToDelete);
            System.out.println("✅ Message supprimé (ID " + messageIdToDelete + ")");

            ///////////////////////////
            // TEST RECLAMATION
            System.out.println("\n=== TEST RECLAMATION ===");
            Reclamation reclamation = new Reclamation(0, "Problème de connexion",
                    "Je n'arrive pas à me connecter.", "en_attente", // Statut corrigé
                    LocalDate.now(), 4);
            reclamationService.create(reclamation);
            System.out.println("✅ Réclamation créée : " + reclamation);

            ///////////////////////////
            // TEST UPDATE RECLAMATION
            System.out.println("\n=== TEST UPDATE RECLAMATION ===");
            Reclamation updatedReclamation = new Reclamation(86, "Problème de connexion",
                    "Connexion rétablie, mise à jour !", "repondu", // Statut corrigé
                    LocalDate.now(), 4);
            reclamationService.update(updatedReclamation);
            System.out.println("✅ Réclamation mise à jour : " + updatedReclamation);

            ///////////////////////////
            // TEST DELETE RECLAMATION
            System.out.println("\n=== TEST DELETE RECLAMATION ===");
            int reclamationIdToDelete = 74;
            reclamationService.deleteById(reclamationIdToDelete);
            System.out.println("✅ Réclamation supprimée (ID " + reclamationIdToDelete + ")");

        } catch (Exception e) {
            System.err.println("\n❌ Erreur critique : " + e.getMessage());
            e.printStackTrace();
        }
    }
}