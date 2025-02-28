package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import models.*;
import okhttp3.*;

import services.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommandeItemAdmin {



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

    @FXML
    private Button pdf;
   // @FXML
   // private Button exp;
    private Commande commande;
    private ValidationCommande vc;

    public void initData(Commande commande) throws Exception {
        this.commande = commande;
        LigneCommandeService ls = new LigneCommandeService();
        User user =ls.getByID(commande.getId());
        nom.setText(user.getNom());
        pren.setText(user.getPrenom());
        tel.setText(String.valueOf(user.getTel()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        date.setText(commande.getDate_com().format(formatter));

        // date.setText(String.valueOf(commande.getDate_com()));
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


        pdf.setOnMouseClicked(event -> {
            try {
                exportToPDF(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void setListComController(ValidationCommande vc) {
        this.vc = vc;
    }
    private void handleDetailClick(MouseEvent event) throws Exception {

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
        cs.updateStatus(commande.getId_com(), "traitée");
        LigneCommandeService ls = new LigneCommandeService();
        User user =ls.getByID(commande.getId());
        int tel =user.getTel();
        String msg="Votre commande est traitée .Vous la recevrez dans un délai de 24 heures";
        sendSMS(String.valueOf(tel), msg);



        vc.reloadCommande(cs.getAll());
    }
  /*  public void handleExp(MouseEvent event) throws Exception {
        CommandeService cs = new CommandeService();
        cs.updateStatus(commande.getId_com(), "expédiée");
        vc.reloadCommande(cs.getAll());
    }*/


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

        String url = "https://386n2m.api.infobip.com/sms/2/text/advanced";


        String jsonBody = String.format("{\"messages\":[{\"destinations\":[{\"to\":\"%s\"}],\"from\":\"447491163443\",\"text\":\"%s\"}]}", phoneNumber1, message);


        OkHttpClient client = new OkHttpClient();


        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonBody, mediaType);


        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Authorization", "App " + apiKey) // Ajoutez votre clé API
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();


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
    @FXML
    private void exportToPDF(MouseEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la facture");
        fileChooser.setInitialFileName("Facture.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF (*.pdf)", "*.pdf"));

        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (file == null) return;

        String filePath = file.getAbsolutePath();

        EquipementService es = new EquipementService();
        LigneCommandeService ls = new LigneCommandeService();
        StockService ss = new StockService();
        UserService us = new UserService();
        User user = ls.getByID(commande.getId());

        List<Lignecommande> lc = ls.getAllByIDC(commande.getId_com());
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Ajouter le titre
        Paragraph title = new Paragraph("Facture", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Ajouter les informations du client
        document.add(new Paragraph("Client: " + user.getNom() + " " + user.getPrenom()));
        document.add(new Paragraph("Date de la facture: " + LocalDate.now()));
        document.add(new Paragraph("\n"));

        // Créer la table des produits
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Reference");
        table.addCell("Produit");
        table.addCell("Quantité");
        table.addCell("Prix");
        table.addCell("Prix total");

        double totalAPayer = 0;
        for (Lignecommande l : lc) {
            Equipement eq = es.getEquipementById(l.getId_e());
            Stock s = ss.getStockById(eq.getId());
            table.addCell(eq.getReference());
            table.addCell(eq.getNom());
            table.addCell(String.valueOf(l.getQuantite()));
            table.addCell(String.valueOf(s.getPrixvente()));
            table.addCell(String.valueOf(l.getPrix_unitaire() * l.getQuantite()));
            totalAPayer += l.getPrix_unitaire() * l.getQuantite();
        }

        // Ajouter le total à payer
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Total à payer");
        table.addCell(String.valueOf(totalAPayer));

        document.add(table);


        for (int i = 0; i < 10; i++) {
            document.add(new Paragraph("\n"));
        }


        PdfContentByte canvas = writer.getDirectContent();
        canvas.setLineWidth(1f);
        canvas.moveTo(document.leftMargin(), document.bottomMargin() + 50);
        canvas.lineTo(document.getPageSize().getWidth() - document.rightMargin(), document.bottomMargin() + 50);
        canvas.stroke();


        float x = document.getPageSize().getWidth() / 2;
        float y = document.bottomMargin() + 40;


        Font entrepriseFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Entreprise : AutoHeaven", entrepriseFont), x, y, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Tel : +21698562433", entrepriseFont), x, y - 15, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Fax : 78965236", entrepriseFont), x, y - 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Email : contact@AutoHeaven.com", entrepriseFont), x, y - 45, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Adresse : Cite El Ghazela, Ariana 1080", entrepriseFont), x, y - 60, 0);

        document.close();
        showAlert("Export réussi", "Le fichier PDF a été généré avec succès à l'emplacement : " + filePath);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
