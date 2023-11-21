package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.out;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.services.PaymentService;
import org.springframework.stereotype.Component;

@Component("StripePaymentService")
public class StripePaymentService implements PaymentService {
    @Override
    public boolean pay(float money, String currencyCode) {
        return money < 100;
    }
}
