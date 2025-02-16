//package org.example;

import models.Voiture;
import models.Categorie;
import services.VoitureService;
import services.CategorieService;

import java.util.List;

//public class Main {
  //  public static void main(String[] args) {
        // try {
        // Création des services
        // CategorieService categorieService = new CategorieService();
        // VoitureService voitureService = new VoitureService();

        // -------------------- TEST CRUD CATEGORIE --------------------

        // 1. Création d'une catégorie
        // Categorie categorie = new Categorie(0, "SUV", "Essence", "Urbaine", 5, "Automatique");
        // CategorieService.create(categorie);
        // System.out.println("✅ Catégorie créée : " + categorie);

        // 2. Récupération et affichage de toutes les catégories
        //  List<Categorie> categories = CategorieService.getAll();
        //  System.out.println("📋 Liste des catégories :");
        //  for (Categorie cat : categories) {
        //      System.out.println(cat);
        //  }

        // 3. Mise à jour de la catégorie
        //  categorie.setType("SUV de Luxe");
        //  CategorieService.update(categorie);
        //  System.out.println("✅ Catégorie mise à jour : " + categorie);

        // -------------------- TEST CRUD VOITURE --------------------

        // 4. Création d'une voiture associée à cette catégorie
        //  Voiture voiture = new Voiture(0, "BMW", "SUV performant", 15000, "Noir", 55000.0, "bmw_suv.jpg", categorie.getId_c(), "oui");
        //  VoitureService.create(voiture);
        //  System.out.println("✅ Voiture créée : " + voiture);

        // 5. Récupération et affichage de toutes les voitures
        //  List<Voiture> voitures = VoitureService.getAll();
        //  System.out.println("📋 Liste des voitures :");
        //  for (Voiture v : voitures) {
        //    System.out.println(v);
        // }

        // 6. Mise à jour de la voiture
        // voiture.setPrix(53000.0);
        // VoitureService.update(voiture);
        // System.out.println("✅ Voiture mise à jour : " + voiture);

        // -------------------- SUPPRESSION --------------------

        // 7. Suppression de la voiture
        // VoitureService.delete(voiture.getId_v());
        // System.out.println("✅ Voiture supprimée avec succès.");

        // 8. Suppression de la catégorie
 //   }