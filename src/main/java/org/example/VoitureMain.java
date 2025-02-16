package org.example;

import models.Voiture;
import models.Categorie;
import services.VoitureService;
import services.CategorieService;

import java.util.List;

public class VoitureMain {
    public static void main(String[] args) {
        try {
            // Création des services
            CategorieService categorieService = new CategorieService();
            VoitureService voitureService = new VoitureService();

            // -------------------- TEST CRUD CATEGORIE --------------------

            // 1. Création d'une catégorie
            Categorie categorie = new Categorie(3, "SUV", "Essence", "Urbaine", 5, "Automatique");
            try {
                categorieService.create(categorie);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("✅ Catégorie créée : " + categorie);

            // 2. Récupération et affichage de toutes les catégories
            List<Categorie> categories = null;
            try {
                categories = categorieService.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("📋 Liste des catégories :");
            for (Categorie cat : categories) {
                System.out.println(cat);
            }

            // 3. Mise à jour de la catégorie
            categorie.setType("SUV de Luxe");
            try {
                categorieService.update(categorie);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("✅ Catégorie mise à jour : " + categorie);

            // -------------------- TEST CRUD VOITURE --------------------

            // 4. Création d'une voiture associée à cette catégorie
            Voiture voiture = new Voiture(1, "BMW", "SUV performant", 15000, "Noir", 55000.0, "bmw_suv.jpg", categorie.getId_c(), "oui");
            try {
                voitureService.create(voiture);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("✅ Voiture créée : " + voiture);

            // 5. Récupération et affichage de toutes les voitures
            List<Voiture> voitures = null;
            try {
                voitures = voitureService.getAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("📋 Liste des voitures :");
            for (Voiture v : voitures) {
                System.out.println(v);
            }

            // 6. Mise à jour de la voiture
            voiture.setPrix(53000.0);
            try {
                voitureService.update(voiture);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("✅ Voiture mise à jour : " + voiture);

            // -------------------- SUPPRESSION --------------------

            // 7. Suppression de la voiture
         //   try {
         //       voitureService.delete(voiture.getId_v());
         //   } catch (Exception e) {
         //       throw new RuntimeException(e);
         //   }
          //  System.out.println("✅ Voiture supprimée avec succès.");

            // 8. Suppression de la catégorie
           // try {
           //     categorieService.delete(categorie.getId_c());
           // } catch (Exception e) {
            //    throw new RuntimeException(e);
           // }
           // System.out.println("✅ Catégorie supprimée avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
