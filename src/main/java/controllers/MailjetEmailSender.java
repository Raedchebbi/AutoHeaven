package controllers;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class MailjetEmailSender {

    public static void main(String[] args) throws MailjetException {
        /*
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(System.getenv("57ada37b472cd0ed4e274014b36d6901"), System.getenv("b26a69fcf8763e87ab81dfc64dd80248"), new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "$SENDER_EMAIL")
                                        .put("Name", "Me"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", "$RECIPIENT_EMAIL")
                                                .put("Name", "You")))
                                .put(Emailv31.Message.SUBJECT, "My first Mailjet Email!")
                                .put(Emailv31.Message.TEXTPART, "Greetings from Mailjet!")
                                .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href=\"https://www.mailjet.com/\">Mailjet</a>!</h3><br />May the delivery force be with you!")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
*/    }

}