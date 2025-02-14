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
        // Création des services
        UserService userService = new UserService();
        ReclamationService reclamationService = new ReclamationService();
        MessagerieService messagerieService = new MessagerieService();

        try {
            /////////////////////////// ==== TEST MESSAGERIE ====
            System.out.println("🔹 Envoi d'un message...");
            Messagerie message = new Messagerie("client", "Bonsoir, comment allez-vous ?", "admin", LocalDateTime.now(), 75, 3);
            messagerieService.create(message);
            System.out.println("✅ Message envoyé !");
            System.out.println(messagerieService.getAll());

            /////////////////////////////// ==== TEST UPDATE MESSAGERIE ====
            System.out.println("🔹 Mise à jour du message...");
            Messagerie updatedMessage = new Messagerie(58, "client", "Message mis à jour !", "client", LocalDateTime.now(), 75, 3);
            messagerieService.update(updatedMessage);
            System.out.println("✅ Message mis à jour !");
            System.out.println(messagerieService.getAll());

            ////////////////////////// ==== TEST DELETE MESSAGERIE ====
            System.out.println("🔹 Suppression d'un message...");
            messagerieService.delete(76); // Suppression par ID
            System.out.println("✅ Message supprimé !");
            System.out.println(messagerieService.getAll());

            /////////////////////////// ==== TEST RECLAMATION ====
            System.out.println("🔹 Création d'une réclamation...");
            Reclamation reclamation = new Reclamation("Problème de connexion", "Je n'arrive pas à me connecter.", "rejetee", LocalDate.now(), 4);
            reclamationService.create(reclamation);
            System.out.println("✅ Réclamation créée !");
            System.out.println(reclamationService.getAll());

            ////////////////////////// ==== TEST UPDATE RECLAMATION ====
            System.out.println("🔹 Mise à jour de la réclamation...");
            Reclamation updatedReclamation = new Reclamation(76, "Problème de connexion", "Connexion rétablie, mise à jour !", "aceptee", LocalDate.now(), 4);
            reclamationService.update(updatedReclamation);
            System.out.println("✅ Réclamation mise à jour !");
            System.out.println(reclamationService.getAll());

            ////////////////////////////// ==== TEST DELETE RECLAMATION ====
            System.out.println("🔹 Suppression d'une réclamation...");
            Reclamation reclamationToDelete = new Reclamation();
            reclamationToDelete.setIdRec(77); // Remplacez par l'ID de la réclamation à supprimer
            reclamationService.delete(reclamationToDelete);  // Suppression par l'objet Reclamation
            System.out.println("✅ Réclamation supprimée !");
            System.out.println(reclamationService.getAll());

            // Vérifier si les messages associés sont supprimés
            System.out.println(messagerieService.getAll());

        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
