package example;

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



        try {
            /*
            User newUser = new User(98760532,
                                    "am",
                                    "Ben Ali",
                                    55443322,
                                    "sami.benali@email.com",
                                    "securePass",
                                    "mecanicien",
                                    "Rue Ibn Khaldoun, Sfax, 3000",
                                    "am_benali");
            try {
                userService.create(newUser);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("User cree : " + newUser);
            */
            User existingUser = userService.getById(41);
            if (existingUser == null) {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
                return;
            }
            System.out.println("Utilisateur récupéré : " + existingUser);

            /*
            Categorie categorie = new Categorie(3,
                                                "SUV",
                                                "Essence",
                                                "Urbaine",
                                                5,
                                                "Automatique");
            try {
                categorieService.create(categorie);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Categorie cree : " + categorie);
            */
            Categorie existingCategorie = categorieService.getById(5);
            if (existingUser == null) {
                System.out.println("Aucun categories trouve avec cet ID.");
                return;
            }
            System.out.println("Categorie recupere : " + existingCategorie);


            Voiture voiture = new Voiture(  1,
                                            "BMW",
                                            "SUV performant",
                                            15000,
                                            "Noir",
                                            55000.0,
                                            "bmw_suv.jpg",
                                            existingCategorie.getId_c(),
                                            "oui");
            try {
                voitureService.create(voiture);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Voiture cree : " + voiture);
/*
            Voiture existVoiture = voitureService.getById(15);
            if (existVoiture == null) {
                System.out.println("Aucun voiture trouve avec cet ID.");
                return;
            }
            System.out.println("Voiture recupere : " + existVoiture);

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

            System.out.println("*****************************************" +
                               "*****************************************");

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
            */

                        // Initialize services
                        TestDriveService testDriveService = new TestDriveService();
                        MecanicienService mechanicService = new MecanicienService();
                        RemorquageService towTruckService = new RemorquageService();

                        // Create test data
                        TestDrive testDrive = new TestDrive(existingUser.getId(), new Date(), "testdrive", 15);
                        Mecanicien mechanic = new Mecanicien( 41, new Date(), "mecanicien", 42, "Engine repair");
                        Remorquage towTruck = new Remorquage( 41, new Date(), "remorquage", 2, "tunis", "ariana");

                        // Create TestDrive reservation
                        testDriveService.create(testDrive);
                        System.out.println("TestDrive created successfully.");

                        // Create Mechanic reservation
                        mechanicService.create(mechanic);
                        System.out.println("Mechanic created successfully.");

                        // Create TowTruck reservation
                        towTruckService.create(towTruck);
                        System.out.println("TowTruck created successfully.");

                        // Get all TestDrive reservations
                        List<TestDrive> testDrives = testDriveService.getAll();
                        System.out.println("All TestDrive reservations:");
                        for (TestDrive td : testDrives) {
                            System.out.println(td);
                        }

                        // Get and print updated TestDrive reservation
                        TestDrive updatedTestDrive = testDriveService.getById(testDrive.getId_res());
                        System.out.println("Updated TestDrive reservation: " + updatedTestDrive);

                        // Get all remaining Mechanic reservations
                        List<Mecanicien> mechanics = mechanicService.getAll();
                        System.out.println("Remaining Mechanic reservations:");
                        for (Mecanicien m : mechanics) {
                            System.out.println(m);
                        }

                        // Get all TowTruck reservations
                        List<Remorquage> towTrucks = towTruckService.getAll();
                        System.out.println("Remaining TowTruck reservations:");
                        for (Remorquage tt : towTrucks) {
                            System.out.println(tt);
                        }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
