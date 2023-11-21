package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.requests;

import java.util.UUID;

public record QuoteItemRequest(UUID id, int quantity) {
}
