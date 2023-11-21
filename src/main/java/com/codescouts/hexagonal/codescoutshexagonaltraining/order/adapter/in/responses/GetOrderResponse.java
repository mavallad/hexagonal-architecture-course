package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.responses;

import java.util.UUID;

public class GetOrderResponse {
    public final UUID id;
    public final String status;

    public GetOrderResponse(UUID id, String status) {
        this.id = id;
        this.status = status;
    }
}
