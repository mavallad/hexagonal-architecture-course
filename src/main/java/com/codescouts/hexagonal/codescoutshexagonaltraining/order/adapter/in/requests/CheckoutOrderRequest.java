package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.requests;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.commands.OrderProductLine;
import jakarta.validation.constraints.NotBlank;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CheckoutOrderRequest {
    @NotBlank(message = "customerId is required")
    public UUID customerId;

    @NotBlank(message = "items are required")
    public QuoteItemRequest[] items;

    @NotBlank(message = "currencyCode is required")
    public String currencyCode;

    public UUID customer() {
        return this.customerId;
    }

    public List<OrderProductLine> items() {
        return Arrays.stream(this.items)
                .map(item ->
                        new OrderProductLine(item.id(), item.quantity()))
                .toList();
    }
}
