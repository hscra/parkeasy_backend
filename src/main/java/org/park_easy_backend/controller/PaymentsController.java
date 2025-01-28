package org.park_easy_backend.controller;
import com.stripe.Stripe;
import lombok.RequiredArgsConstructor;
import org.park_easy_backend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentService paymentService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestParam long amount, @RequestParam String currency) {
        try {
            var paymentIntent = paymentService.createPaymentIntent(amount, currency);
            return ResponseEntity.ok(paymentIntent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating payment intent: " + e.getMessage());
        }
    }

    @PostMapping("/create-customer")
    public ResponseEntity<?> createCustomer(@RequestParam String email, @RequestParam String name) {
        try {
            var customer = paymentService.createCustomer(email, name);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating customer: " + e.getMessage());
        }
    }

    @GetMapping("/get-payment-intent")
    public ResponseEntity<?> getPaymentIntent(@RequestParam String paymentIntentId) {
        try {
            var paymentIntent = paymentService.getPaymentIntent(paymentIntentId);
            return ResponseEntity.ok(paymentIntent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving payment intent: " + e.getMessage());
        }
    }

    @PostMapping("/cancel-payment-intent")
    public ResponseEntity<?> cancelPaymentIntent(@RequestParam String paymentIntentId) {
        try {
            var canceledPaymentIntent = paymentService.cancelPaymentIntent(paymentIntentId);
            return ResponseEntity.ok(canceledPaymentIntent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error canceling payment intent: " + e.getMessage());
        }
    }

}
