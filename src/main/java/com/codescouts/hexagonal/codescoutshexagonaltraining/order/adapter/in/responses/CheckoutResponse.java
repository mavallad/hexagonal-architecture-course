package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.responses;

import java.util.UUID;

public class CheckoutResponse {
    public final UUID id;
    public final String status;

    public CheckoutResponse(UUID id, String status) {
        this.id = id;
        this.status = status;
    }
}
