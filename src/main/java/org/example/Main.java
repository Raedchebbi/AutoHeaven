package org.example;

import models.voiture;
import services.voitureService;
import models.categorie;
import services.categorieService;
import utils.MyDb;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        voitureService us = new voitureService();
        categorieService cs = new categorieService();
        try {

            // categorie cat = new categorie(1,2, "Sportive", "Essence", "Luxe", "Automatique");
            // cs.create(cat);
            //  System.out.println("Catégorie créée avec succès !");

            // categorie catToDelete = new categorie();
            // catToDelete.setId_c(6);
            // cs.delete(catToDelete);
            // System.out.println("Catégorie supprimée avec succès !");

            //  categorie catToUpdate = new categorie(7, 5, "SUV", "kirozén", "sport", "Automatique");
            //  cs.update(catToUpdate);
            //  System.out.println("Catégorie mise à jour avec succès !");

            // List<categorie> categories = cs.getAll();
            // System.out.println("Liste des catégories :");
            // for (categorie c : categories) {
            //    System.out.println(c);
            //}


            //  voiture voi = new voiture(33, 30000, "BMW", "Voiture de luxe sportive", "noir",184250);
            //  us.create(voi);
            // System.out.println("Voiture créée avec succès !");


            //     voiture vToUpdate = new voiture( 23, 200, "katrel", "Rouge", "rouge", 200);
            //    us.update(vToUpdate);
            //    System.out.println("Voiture mise à jour avec succès !");


          //  voiture vToDelete = new voiture();
          //  vToDelete.setId_v(1);
           //   us.delete(vToDelete);
            //    System.out.println("Voiture supprimée avec succès !");


        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}