package org.park_easy_backend.controller;
import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.StripeRequest;
import org.park_easy_backend.dto.StripeResponse;
import org.park_easy_backend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody StripeRequest req) {
        StripeResponse res = paymentService.checkout(req);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
