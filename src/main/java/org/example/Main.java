package org.example;

import models.User;
import models.Reclamation;
import models.Messagerie;
import service.ReclamationService;
import service.MessagerieService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Création des services
        ReclamationService reclamationService = new ReclamationService();
        MessagerieService messagerieService = new MessagerieService();

        try {


            /////////////////////////// ==== TEST MESSAGERIE ====
            Messagerie message = new Messagerie("client", "bonsoir, comment allez-vous ?", "admin", LocalDateTime.now(), 43);
            messagerieService.create(message);
            System.out.println("✅ Message envoyé !");
            System.out.println(messagerieService.getAll());




            /////////////////////////////// ==== TEST UPDATE MESSAGERIE ====
            Messagerie updatedMessage = new Messagerie(58, "client", "Message mis à jour !", "client", LocalDateTime.now(), 43);
            messagerieService.update(updatedMessage);
            System.out.println("✅ Message mis à jour !");
            System.out.println(messagerieService.getAll());




            ////////////////////////// ==== TEST DELETE MESSAGERIE ====
            Messagerie messageToDelete = new Messagerie();
            messageToDelete.setId_m(59); // Supposons que l'ID du message à supprimer est 4
            messagerieService.delete(messageToDelete);
            System.out.println("✅ Message supprimé !");
            System.out.println(messagerieService.getAll());


/////////////////////////// ==== TEST RECLAMATION ====
            Reclamation reclamation = new Reclamation("Problème de connexion", "Je n'arrive pas à me connecter.", "rejetee", LocalDate.now(), 4);
            reclamationService.create(reclamation);
            System.out.println("✅ Réclamation créée !");
            System.out.println(reclamationService.getAll());




            ////////////////////////// ==== TEST UPDATE RECLAMATION ====
            Reclamation updatedReclamation = new Reclamation(50, "Problème de connexion", "Connexion rétablie, mise à jour !", "rejetee", LocalDate.now(), 4);
            reclamationService.update(updatedReclamation);
            System.out.println("✅ Réclamation mise à jour !");
            System.out.println(reclamationService.getAll());




            ////////////////////////////// ==== TEST DELETE RECLAMATION ====
            Reclamation reclamationToDelete = new Reclamation();
            reclamationToDelete.setId(50); // ID de la réclamation à supprimer
            reclamationService.delete(reclamationToDelete);

            System.out.println("✅ Réclamation supprimée !");
            System.out.println(reclamationService.getAll());

            // Vérifier si les messages associés sont supprimés
            System.out.println(messagerieService.getAll());






        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
