package org.example;

import models.avis;
import service.AvisService;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crée une instance du service
        AvisService us = new AvisService();

        try {
            // Appel à la méthode getAll pour récupérer tous les avis
            List<avis> avisList = us.getAll();

            // Afficher tous les avis
            if (avisList.isEmpty()) {

                System.out.println("Aucun avis trouvé.");
            } else {
                for (avis a : avisList) {
                    System.out.println(a);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
