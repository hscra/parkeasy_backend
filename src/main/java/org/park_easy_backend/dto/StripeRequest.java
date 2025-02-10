package org.park_easy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeRequest {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
}
