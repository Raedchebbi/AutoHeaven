package services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Commande;


public class JavaConnector {
    @FXML
    private WebView webView;



    public void processPayment(String paymentMethodId , Commande commande) {
            // Traiter le paiement avec Stripe
            System.out.println("Payment Method ID: " + paymentMethodId);

            // Appeler l'API Stripe pour confirmer le paiement
            try {
                Stripe.apiKey = "sk_test_XXXXXXXXXXXXXXXXXXXXXXXX"; // Clé secrète Stripe

                PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                        .setAmount((long) (commande.getMontant_total() * 100)) // Montant en cents
                        .setCurrency("eur") // Devise
                        .setPaymentMethod(paymentMethodId)
                        .setConfirm(true)
                        .build();

                PaymentIntent paymentIntent = PaymentIntent.create(params);
                System.out.println("Paiement réussi: " + paymentIntent.getId());

                // Mettre à jour le statut de la commande
                CommandeService cs = new CommandeService();
                cs.updateStatus(commande.getId_com(), "payee");

                // Fermer la fenêtre de paiement
                Stage stage = (Stage) webView.getScene().getWindow();
                stage.close();

                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Paiement réussi");
                alert.setHeaderText(null);
                alert.setContentText("Le paiement pour la commande #" + commande.getId_com() + " a été effectué avec succès.");
                alert.showAndWait();

            } catch (StripeException e) {
                System.err.println("Erreur lors du paiement: " + e.getMessage());

                // Afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de paiement");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors du paiement: " + e.getMessage());
                alert.showAndWait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


