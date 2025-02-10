package org.park_easy_backend.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import jakarta.annotation.PostConstruct;
import org.park_easy_backend.dto.StripeRequest;
import org.park_easy_backend.dto.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${STRIPE_KEY}")
    private String key;

    @Value("${CLIENT_DOMAIN}")
    private String clientDomain;

    private String defaultCurrency = "USD";

    @PostConstruct
    public void init(){
        Stripe.apiKey = key;
    }

    public StripeResponse checkout(StripeRequest req) {
        ProductData productData = ProductData.builder()
                .setName(req.getName()).build();


        PriceData priceData = PriceData.builder()
                .setCurrency(req.getCurrency() == null ? defaultCurrency : req.getCurrency())
                .setUnitAmount(req.getAmount())
                .setProductData(productData)
                .build();

        LineItem lineItem = LineItem.builder()
                .setQuantity(req.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(clientDomain + "/success")
                .setCancelUrl(clientDomain + "/cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null;

        try {
            session = Session.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUri(session.getUrl())
                .build();
    }
}
