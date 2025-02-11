package org.example;

import models.Adresse;
import services.AdresseService;
import services.UserService;
import models.User;

public class Main {
    public static void main(String[] args) {
        AdresseService adresseService = new AdresseService();
        UserService userService = new UserService();

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

            // update adresse
            adresse.setRue("Avenue Habib Bourguiba");
            adresse.setNomBatiment("Immeuble Al-Fourat");
            adresse.setNumeroPorte("7");
            adresseService.update(adresse);
            System.out.println("Adresse mise à jour : " + adresse);

            //update user
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

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
