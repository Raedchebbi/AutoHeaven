package services;

import com.stripe.Stripe;

public class StripeConfig {
    public static void initialize() {

        Stripe.apiKey = "sk_test_51QwhI1H4NaQY1zAhv8ezh7HtXMNMTfgMKcGiZhkUjzguNONfGJJXC0XyAgBmEf60fx3Afuq7C1rPa1yZl8AqaaM900gbo7OuIk";  // Ta clé secrète Stripe
    }
}
