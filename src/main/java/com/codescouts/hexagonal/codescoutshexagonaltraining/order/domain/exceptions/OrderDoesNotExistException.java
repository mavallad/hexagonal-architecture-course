package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions;

import java.util.UUID;

public class OrderDoesNotExistException extends Throwable {
    public OrderDoesNotExistException(UUID orderId) {
        super("Order with id " + orderId + " does not exist");
    }
}
