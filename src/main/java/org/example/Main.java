/*package org.example;

import models.Offre;
import models.User;
import services.OffreService;
import services.UserService;

public class Main {
    public static void main(String[] args) {
        OffreService offreService = new OffreService();
        UserService userService = new UserService();

        try {
            // Ajout user avec adresse et username
            User newUser = new User(0, 98765532, "Sami", "Ben Ali", 55443322, "sami.benali@email.com", "securePass", "client", "Rue Ibn Khaldoun, Sfax, 3000", "sami_benali");
            userService.create(newUser);  // Créer l'utilisateur dans la base de données

            // Ajout offre
            Offre offre = new Offre(0, "Réduction de 20% sur tous les équipements", "Offre valable sur tous les équipements en magasin", 20.0, "2025-02-01", "2025-02-28", 1);
            offreService.create(offre);

            // Affichage des offres
            System.out.println("Liste des offres : ");
            offreService.getAll().forEach(o -> System.out.println(o));

            // Mise à jour de l'offre
            offre.setType_offre("Réduction de 30% sur les équipements");
            offre.setDescription("Nouvelle offre spéciale sur tous les équipements.");
            offre.setTaux_reduction(30.0);
            offreService.update(offre);
            System.out.println("Offre mise à jour : " + offre);

            // Mise à jour de l'utilisateur (avec changement d'adresse et de username)
            newUser.setNom("Ahmed");
            newUser.setPrenom("Ben Mohamed");
            newUser.setEmail("ahmed.benmohamed@email.com");
            newUser.setTel(55443311);
            newUser.setAdresse("Rue Mongi Slim, Tunis, 1000");
            newUser.setUsername("ahmed_benmohamed"); // Mise à jour du username
            userService.update(newUser);
            System.out.println("Utilisateur mis à jour : " + newUser.getNom() + " " + newUser.getPrenom());

            // Suppression de l'offre
            offreService.delete(offre);

            // Suppression de l'utilisateur
            userService.delete(newUser);

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
*/