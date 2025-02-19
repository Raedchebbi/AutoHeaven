package org.example;

import models.*;
import services.*;
import utils.MyDb;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = MyDb.getInstance().getConn();

        UserService userService = new UserService();
        CategorieService categorieService = new CategorieService();
        VoitureService voitureService = new VoitureService();
        ReservationService reservationService = new ReservationService();
        VoitureRemorquageService voitureRemorquageService = new VoitureRemorquageService();
        ServiceRemorquageService serviceRemorquageService = new ServiceRemorquageService();


        try {
            /*User newUser = new User(98765532, "Sami", "Ben Ali", 55443322, "sami.benali@email.com", "securePass", "client", "Rue Ibn Khaldoun, Sfax, 3000", "sami_benali");
            userService.create(newUser);

            Categorie categorie = new Categorie(3, "SUV", "Essence", "Urbaine", 5, "Automatique");
            try {
                categorieService.create(categorie);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Categorie cree : " + categorie);

            Voiture voiture = new Voiture(1, "BMW", "SUV performant", 15000, "Noir", 55000.0, "bmw_suv.jpg", existingCategorie.getId_c(), "oui");
            try {
                voitureService.create(voiture);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Voiture cree : " + voiture);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date_res = new Date();
            Reservation newReservation = new Reservation(0, date_res, "confirmee", existVoiture.getId_v(), existingUser.getId());
            reservationService.create(newReservation);
            Reservation createdReservation = reservationService.getById(newReservation.getId_r());
            System.out.println("Reservation cree : " + createdReservation);

            VoitureRemorquage nouvelleVoiture = new VoitureRemorquage(0, "Standard", 2020, "AG123", "disponible");
            voitureRemorquageService.create(nouvelleVoiture);
            System.out.println("Nouvelle voiture ajoutée : " + nouvelleVoiture);
            */


            User existingUser = userService.getById(41);
            if (existingUser == null) {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
                return;
            }
            System.out.println("Utilisateur récupéré : " + existingUser);

            Categorie existingCategorie = categorieService.getById(5);
            if (existingUser == null) {
                System.out.println("Aucun categories trouve avec cet ID.");
                return;
            }
            System.out.println("Categorie recupere : " + existingCategorie);

            Voiture existVoiture = voitureService.getById(15);
            if (existVoiture == null) {
                System.out.println("Aucun voiture trouve avec cet ID.");
                return;
            }
            System.out.println("Voiture recupere : " + existVoiture);

            VoitureRemorquage existVoitureRem = voitureRemorquageService.getById(1);
            if (existVoitureRem == null) {
                System.out.println("Aucun voiture Remorquage trouve avec cet ID.");
                return;
            }
            System.out.println("Voiture Remorquage recupere : " + existVoitureRem);

            ServiceRemorquage nouveauService = new ServiceRemorquage(0, 1, "John Doe", "123 Rue de Paris", new Date(), "en cours");
            serviceRemorquageService.create(nouveauService);
            System.out.println("Nouveau service remorquage ajoute : " + nouveauService);


            /*
            newUser.setNom("Ahmed");
            newUser.setPrenom("Ben Mohamed");
            newUser.setEmail("ahmed.benmohamed@email.com");
            newUser.setTel(55443311);
            newUser.setAdresse("Rue Mongi Slim, Tunis, 1000");
            newUser.setUsername("ahmed_benmohamed"); // Mise à jour du username
            userService.update(newUser);
            System.out.println("Utilisateur mis à jour : " + newUser.getNom() + " " + newUser.getPrenom());

            List<Categorie> categories = null;
            try {
                categories = categorieService.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Liste des catégories :");
            for (Categorie cat : categories) {
                System.out.println(cat);
            }

            voiture.setPrix(53000.0);
            try {
                voitureService.update(voiture);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Voiture mise à jour : " + voiture);

            if (!services.isEmpty()) {
                ServiceRemorquage serviceAModifier = services.get(0);
                serviceAModifier.setStatut("terminé");
                service.update(serviceAModifier);
                System.out.println("Service mis à jour : " + serviceAModifier);
            }
            */


            System.out.println("*****************************************" +
                    "*****************************************");
            List<Categorie> categories = null;
            try {
                categories = categorieService.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Liste des catégories :");
            for (Categorie cat : categories) {
                System.out.println(cat);
            }
            List<Voiture> voitures = null;
            try {
                voitures = voitureService.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Liste des voitures :");
            for (Voiture v : voitures) {
                System.out.println(v);
            }
            List<VoitureRemorquage> voiture_remorquage = null;
            try {
                voiture_remorquage = voitureRemorquageService.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Liste des voitures Remorquage:");
            for (VoitureRemorquage vr : voiture_remorquage) {
                System.out.println(vr);
            }
            List<ServiceRemorquage> service_remorquage = null;
            try {
                service_remorquage = serviceRemorquageService.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Liste des Services Remorquage:");
            for (ServiceRemorquage service : service_remorquage) {
                System.out.println(service);
            }




            /*
            try {
                userService.delete(existingUser);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("User supprimee avec succes.");

            try {
                    voitureService.delete(existVoiture.getId_v());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            System.out.println("Voiture supprimee avec succes.");

            try {
                voitureRemorquageService.delete(existVoitureRem.getId_vr());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Voiture remorquage supprimee avec succees.");

            try {
                serviceRemorquageService.delete(nouveauService.getId_s());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Service supprimee avec succes.");
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}