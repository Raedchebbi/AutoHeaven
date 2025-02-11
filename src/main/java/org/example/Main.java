package org.example;

import models.Adresse;
import services.AdresseService;
import services.UserService;
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
        AdresseService adresseService = new AdresseService();
        UserService userService = new UserService();
        ReclamationService reclamationService = new ReclamationService();
        MessagerieService messagerieService = new MessagerieService();

        try {
            // Ajout user
            User newUser = new User(0, 98765532, "Sami", "Ben Ali", 55443322, "sami.benali@email.com", "securePass", "client");
            userService.create(newUser);  // Créer l'utilisateur dans la base de données
            System.out.println("Nouvel utilisateur ajouté : " + newUser.getNom() + " " + newUser.getPrenom());
            System.out.println("Utilisateur ajouté avec ID : " + newUser.getId());

            // Ajout adresse
            Adresse adresse = new Adresse(0, "Rue Ibn Khaldoun", "Sfax", "3000", "Immeuble El-Naoufel", "5", newUser.getId());
            adresseService.create(adresse);
            System.out.println("Adresse ajoutée pour l'utilisateur " + newUser.getNom() + " " + newUser.getPrenom() + " : " + adresse);

            // Affichage adresses
            System.out.println("Liste des adresses : " + adresseService.getAll());

            // Update adresse
            adresse.setRue("Avenue Habib Bourguiba");
            adresse.setNomBatiment("Immeuble Al-Fourat");
            adresse.setNumeroPorte("7");
            adresseService.update(adresse);
            System.out.println("Adresse mise à jour : " + adresse);

            // Update user
            newUser.setNom("Ahmed");
            newUser.setPrenom("Ben Mohamed");
            newUser.setEmail("ahmed.benmohamed@email.com");
            newUser.setTel(55443311);
            userService.update(newUser);
            System.out.println("Utilisateur mis à jour : " + newUser.getNom() + " " + newUser.getPrenom());

            // Supprimer l'adresse
            adresseService.delete(adresse);  // Supprimer l'adresse dans la base de données
            System.out.println("Adresse supprimée.");

            // Supprimer user
            userService.delete(newUser);  // Supprimer l'utilisateur dans la base de données
            System.out.println("Utilisateur supprimé.");

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
