package controllers;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.Configuration;
import com.infobip.api.SendSmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import okhttp3.*;

import models.Commande;
import models.EquipementAffichage;
import models.Lignecommande;
import models.User;
import services.CommandeService;
import services.LigneCommandeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandeItemAdmin {

    @FXML
    private Button cancel;

    @FXML
    private Button check;

    @FXML
    private Label date;

    @FXML
    private FontAwesomeIconView detail;

    @FXML
    private Label nom;

    @FXML
    private Label pren;

    @FXML
    private Label prix;

    @FXML
    private Label tel;
    private Commande commande;
    private ValidationCommande vc;

    public void initData(Commande commande) throws Exception {
        this.commande = commande;
        LigneCommandeService ls = new LigneCommandeService();
        User user =ls.getByID(commande.getId());
        nom.setText(user.getNom());
        pren.setText(user.getPrenom());
        tel.setText(String.valueOf(user.getTel()));
        date.setText(String.valueOf(commande.getDate_com()));
        //prix.setText(String.valueOf(commande.getMontant_total()));
        prix.setText(commande.getStatus());
        detail.setOnMouseClicked(event -> {
            try {
                handleDetailClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        check.setOnMouseClicked(event -> {
            try {
                handleCheckClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cancel.setOnMouseClicked(event -> {
            try {
                handleCancelClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void setListComController(ValidationCommande vc) {
        this.vc = vc;
    }
    private void handleDetailClick(MouseEvent event) throws Exception {
        // Récupérer les équipements achetés pour cette commande
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        List<Lignecommande> ligneCommandes = new ArrayList<>();
        System.out.println(ligneCommandeService.getAllByIDC(commande.getId_com()));
        for (Lignecommande l :ligneCommandeService.getAllByIDC(commande.getId_com())){
            ligneCommandes.add(l);

        }



        vc.showEquipementsAchetes(ligneCommandes ,commande);
    }
    private void handleCheckClick(MouseEvent event) throws Exception {
        // Mettre à jour le statut de la commande à "confirmee"
        CommandeService cs = new CommandeService();
        cs.updateStatus(commande.getId_com(), "confirmee");
        LigneCommandeService ls = new LigneCommandeService();
        User user =ls.getByID(commande.getId());
        int tel =user.getTel();
        String msg="Votre commande est confirmée .merci pour votre confiance";
        sendSMS(String.valueOf(tel), msg);



        vc.reloadCommande();
    }

    private void handleCancelClick(MouseEvent event) throws Exception {
        // Mettre à jour le statut de la commande à "annulee"
        CommandeService cs = new CommandeService();
        cs.updateStatus(commande.getId_com(), "annulee");

        // Recharger la liste des commandes après la mise à jour
        vc.reloadCommande();
    }
    private void sendSMS(String phoneNumber, String message) {
       /* ApiClient client = Configuration.getDefaultApiClient();
        String BASE_URL = "https://386n2m.api.infobip.com/sms/2/text/advanced";
        String API_KEY = "b0b740ba353f3287fac9daa64415a615-ccdda96c-aafb-4c90-a7dc-862f17d16a3d'";
        client.setApiKeyPrefix("App");
        client.setApiKey(API_KEY);
        client.setBasePath(BASE_URL);

        SendSmsApi sendSmsApi = new SendSmsApi(client);

        SmsTextualMessage smsMessage = new SmsTextualMessage()
                .addDestinationsItem(new SmsDestination().to("phoneNumber"))
                .text(message);

        SmsAdvancedTextualRequest smsRequest = new SmsAdvancedTextualRequest()
                .messages(Collections.singletonList(smsMessage));

        try {
            sendSmsApi.sendSmsMessage(smsRequest);
            System.out.println("SMS sent successfully!");
        } catch (ApiException e) {
            System.err.println("Failed to send SMS: " + e.getResponseBody());
        }*/
        String s ="216";
        String phoneNumber1=s+phoneNumber;
        String apiKey = "b0b740ba353f3287fac9daa64415a615-ccdda96c-aafb-4c90-a7dc-862f17d16a3d";
        // Remplacez par l'URL de votre endpoint Infobip
        String url = "https://386n2m.api.infobip.com/sms/2/text/advanced";

        // Corps de la requête JSON
        String jsonBody = String.format("{\"messages\":[{\"destinations\":[{\"to\":\"%s\"}],\"from\":\"447491163443\",\"text\":\"%s\"}]}", phoneNumber1, message);

        // Créez une instance d'OkHttpClient
        OkHttpClient client = new OkHttpClient();

        // Créez le corps de la requête
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        // Créez la requête HTTP
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Authorization", "App " + apiKey) // Ajoutez votre clé API
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        // Envoyez la requête et traitez la réponse
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("SMS sent successfully!");
                System.out.println("Response: " + response.body().string());
            } else {
                System.err.println("Failed to send SMS: " + response.code() + " - " + response.message());
                System.err.println("Response: " + response.body().string());
            }
        } catch (IOException e) {
            System.err.println("Error sending SMS: " + e.getMessage());
        }
    }


}
