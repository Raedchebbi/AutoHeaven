package services;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.Configuration;
import com.infobip.api.SendSmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;

import java.util.Collections;

public class InfobipClientInitializer {
    private static void sendSMS(String phoneNumber, String message) {
        ApiClient client = Configuration.getDefaultApiClient();
        String BASE_URL = "https://386n2m.api.infobip.com/sms/2/text/advanced";
        String API_KEY = "b0b740ba353f3287fac9daa64415a615-ccdda96c-aafb-4c90-a7dc-862f17d16a3d'";
        client.setApiKeyPrefix("App");
        client.setApiKey(API_KEY);
        client.setBasePath(BASE_URL);

        SendSmsApi sendSmsApi = new SendSmsApi(client);

        SmsTextualMessage smsMessage = new SmsTextualMessage()
                .addDestinationsItem(new SmsDestination().to(phoneNumber))
                .text(message);

        SmsAdvancedTextualRequest smsRequest = new SmsAdvancedTextualRequest()
                .messages(Collections.singletonList(smsMessage));

        try {
            sendSmsApi.sendSmsMessage(smsRequest);
            System.out.println("SMS sent successfully!");
        } catch (ApiException e) {
            System.err.println("Failed to send SMS: " + e.getResponseBody());
        }
    }

}
