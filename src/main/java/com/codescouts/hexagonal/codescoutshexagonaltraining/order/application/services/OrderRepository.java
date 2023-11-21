package com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.services;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Order;

import java.util.UUID;

public interface OrderRepository {
    void save(Order order);

    Order findById(UUID orderId);
}
