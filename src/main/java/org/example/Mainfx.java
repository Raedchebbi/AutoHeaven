package org.example;

import controllers.MailService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class Mainfx extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
        primaryStage.setTitle("PiDev - Gestion des Réclamations (Admin)");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Envoyer un email de succès au lancement
        //sendSuccessEmail();
    }

    /*private void sendSuccessEmail() {
        MailService mailService = new MailService();
        try {
            mailService.sendEmailToAllUsers("Succès 🎉", "L'application a été lancée avec succès !");
            System.out.println("📧 Email envoyé !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
