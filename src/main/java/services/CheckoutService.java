package services;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

public class CheckoutService {
    public static String createCheckoutSession(long amount, String currency) throws Exception {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://checkout.stripe.com/c/pay/success")
                .setCancelUrl("https://checkout.stripe.com/c/pay/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(currency)
                                                .setUnitAmount(amount)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Commande #" + System.currentTimeMillis())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
