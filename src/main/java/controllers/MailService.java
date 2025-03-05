package controllers;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.*;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import models.User;
import org.json.JSONArray;
import services.UserService;
import java.sql.SQLException;
import java.util.List;

public class MailService {
    private static final String API_KEY = "620cc7298b4382b98d561a91e6419547";
    private static final String API_SECRET = "535a5d1a115aae91945ef511baa22cc8";

    public void sendEmailToAllUsers(String subject, String content) throws SQLException {
        UserService userService = new UserService();
        List<String> allEmails = userService.getAllEmails();

        MailjetClient client = new MailjetClient(API_KEY, API_SECRET);

        for (String email : allEmails) {
            TransactionalEmail message = TransactionalEmail.builder()
                    .to(new SendContact(email))
                    .from(new SendContact("rayen.elfil@esprit.tn", "CEO"))
                    .subject(subject)
                    .htmlPart(content)
                    .build();

            try {
                SendEmailsRequest request = SendEmailsRequest.builder().message(message).build();
                SendEmailsResponse response = request.sendWith(client);
                System.out.println("Email envoyé à " + email + " : " + response);
            } catch (MailjetException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'envoi à " + email + " : " + e.getMessage());
            }
        }
    }

    public void sendEmailToUser(int userId, String subject, String content) throws Exception {
        UserService userService = new UserService();
        User user = userService.getById(userId); // Obtenir l'utilisateur par ID

        if (user != null) {
            String email = user.getEmail();

            MailjetClient client = new MailjetClient(API_KEY, API_SECRET);
            TransactionalEmail message = TransactionalEmail.builder()
                    .to(new SendContact(email))
                    .from(new SendContact("rayen.elfil@esprit.tn", "CEO"))
                    .subject(subject)
                    .htmlPart(content)
                    .build();

            try {
                SendEmailsRequest request = SendEmailsRequest.builder().message(message).build();
                SendEmailsResponse response = request.sendWith(client);
                System.out.println("Email envoyé à " + email + " : " + response);
            } catch (MailjetException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de l'envoi à " + email + " : " + e.getMessage());
            }
        } else {
            System.err.println("Utilisateur non trouvé pour l'ID : " + userId);
        }
    }
}
